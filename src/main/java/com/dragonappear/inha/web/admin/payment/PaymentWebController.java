package com.dragonappear.inha.web.admin.payment;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.web.admin.payment.dto.PaymentWebDto;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import com.dragonappear.inha.api.service.buying.iamport.IamportService;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@RequiredArgsConstructor
@Controller
public class PaymentWebController {
    private final PaymentService paymentService;
    private final UserPointService userPointService;
    private final IamportService iamportService;

    @GetMapping("/admin/payments")
    public String getAllPayments(Model model) {
        List<Payment> payments = paymentService.findAll();
        List<PaymentWebDto> dtos = payments.stream().map(payment -> {
            return PaymentWebDto.builder()
                    .paymentId(payment.getId())
                    .price(payment.getPaymentPrice().getAmount())
                    .userId(payment.getUser().getId())
                    .updateDate(payment.getUpdatedDate())
                    .pgName(payment.getPgName())
                    .status(payment.getPaymentStatus())
                    .point(payment.getPoint().getAmount())
                    .impId(payment.getImpId())
                    .merchantId(payment.getMerchantId())
                    .build();
        }).collect(Collectors.toList());
        model.addAttribute("payments", dtos);
        return "payment/paymentList";
    }


    @GetMapping("/admin/payments/cancel/{paymentId}")
    public MessageDto cancelPayment(@PathVariable("paymentId") Long paymentId) {
        Payment payment = paymentService.findById(paymentId);
        userPointService.accumulate(payment.getUser().getId(), new Money(payment.getPoint().getAmount())); // 유저 포인트 복구
            /**
             * 결제테이블수정 로직 필요
             */
        iamportService.cancelPayment(CancelDto.builder()
                    .token(IamportService.getImportToken())
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
