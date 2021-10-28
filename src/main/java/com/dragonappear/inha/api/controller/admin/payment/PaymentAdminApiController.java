package com.dragonappear.inha.api.controller.admin.payment;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.admin.payment.dto.PaymentCancelDto;
import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.api.controller.buying.iamport.CancelDto;
import com.dragonappear.inha.api.controller.buying.iamport.IamportController;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;
import static com.dragonappear.inha.api.returndto.ResultDto.returnResults;


@Api(tags = {"관리자 결제내역 조회 API"})
@RequiredArgsConstructor
@RestController
public class PaymentAdminApiController {
    private final PaymentService paymentService;
    private final UserPointService userPointService;

    @ApiOperation(value = "모든 결제 내역 조회 API", notes = "모든 결제 내역 조회 API")
    @GetMapping("/payments/history")
    public ResultDto getAllPayments() {
        List<Payment> payments = paymentService.findAll();
        List<PaymentCancelDto> dtos = payments.stream().map(payment -> {
            return PaymentCancelDto.builder()
                    .paymentId(payment.getId())
                    .price(payment.getPaymentPrice().getAmount())
                    .updateDate(payment.getUpdatedDate())
                    .pgName(payment.getPgName())
                    .merchantId(payment.getMerchantId())
                    .status(payment.getPaymentStatus())
                    .point(payment.getPoint().getAmount())
                    .build();
        }).collect(Collectors.toList());

        return returnResults(dtos);
    }

    @ApiOperation(value = "결제 취소 API", notes = "결제 취소")
    @GetMapping("/payments/cancel/{paymentId}")
    public MessageDto cancelPayment(@PathVariable("paymentId") Long paymentId) {
        Payment payment = paymentService.findById(paymentId);
        userPointService.accumulate(payment.getUser().getId(), new Money(payment.getPoint().getAmount())); // 유저 포인트 복구
            /**
             * 결제테이블수정 로직 필요
             */

            IamportController.cancelPayment(CancelDto.builder()
                    .token(IamportController.getImportToken())
                    .impId(payment.getImpId())
                    .merchantId(payment.getMerchantId())
                    .amount(payment.getPaymentPrice().getAmount().toString())
                    .checksum(payment.getPaymentPrice().getAmount().toString())
                    .build());
        return MessageDto.builder()
                .message(getMessage("isCancelSuccess", true, "Status", "결제가 취소되었습니다"))
                .build();
    }
}
