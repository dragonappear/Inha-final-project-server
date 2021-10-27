package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.buying.dto.PaymentDto;
import com.dragonappear.inha.api.controller.buying.validation.PaymentValidation;
import com.dragonappear.inha.config.iamport.CancelDto;
import com.dragonappear.inha.config.iamport.IamportConfig;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"구매 결제 정보 저장 API"})
@RestController
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentService paymentService;
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    private final UserPointService userPointService;

    @ApiOperation(value = "결제 정보 저장 API", notes = "결제 정보 저장")
    @PostMapping("/payments/new")
    public MessageDto savePayment(@RequestBody PaymentDto dto) {
        // 결제내역 검증
        try {
            validatePayment(dto);
        } catch (IllegalStateException e1) {
            try {
                IamportConfig.cancelPayment(CancelDto.getCancelDto(dto));
            } catch (Exception e2) {
                return MessageDto.builder()
                        .message(getMessage("isPaySuccess", false, "Status",e1.getMessage()+ e2.getMessage())).build();
            }
            return MessageDto.builder()
                    .message(getMessage("isPaySuccess", false, "Status", e1.getMessage())).build();
        }

        // 결제 생성
        Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
        User user = userService.findOneById(dto.getBuyerId());
        paymentService.save(dto.toEntity(user, auctionitem,new Money(dto.getPoint())));
        if(!dto.getPoint().equals(BigDecimal.ZERO)){
            userPointService.subtract(user.getId(), new Money(dto.getPoint())); //포인트 차감
        }
        return MessageDto.builder()
                .message(getMessage("isPaySuccess", true, "Status", "결제가 완료되었습니다."))
                .build();
    }

    /**
     *  검증로직
     */
    // dto 검증
    private void validatePayment(PaymentDto dto) throws IllegalStateException{
        PaymentValidation validation = PaymentValidation.builder()
                .auctionItemService(auctionItemService)
                .userPointService(userPointService)
                .userService(userService)
                .paymentService(paymentService)
                .build();
        validation.validateImpIdAndMId(dto);
        validation.validatePoint(dto);
        validation.validatePrice(dto);
        validation.validateAuctionitemStatus(dto);
    }

}



