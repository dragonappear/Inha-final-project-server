package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.buying.dto.PaymentDto;
import com.dragonappear.inha.api.controller.buying.validation.PaymentValidation;
import com.dragonappear.inha.config.iamport.CancelDto;
import com.dragonappear.inha.config.iamport.IamportConfig;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.InstantBuying;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.dragonappear.inha.api.controller.buying.validation.PaymentValidation.validationPayment;
import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"즉시구매 API"})
@RestController
@RequiredArgsConstructor
public class BuyingInstantApiController {
    private final PaymentService paymentService;
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    private final UserPointService userPointService;
    private final BuyingService buyingService;

    @ApiOperation(value = "즉시구매 결제 저장 API", notes = "즉시구매 결제 저장")
    @PostMapping("/payments/new/instant")
    public MessageDto savePayment(@RequestBody PaymentDto dto) {
        // 결제내역 검증
        MessageDto result = validationPayment(dto,auctionItemService,userService,userPointService,paymentService);
        if (result.getMessage().get("isValidateSuccess").equals(false)) {
            return MessageDto.builder()
                    .message(getMessage("isPaySuccess", false, "Status", result.getMessage().get("Status").toString()))
                    .build();
        }
        // 결제 생성
        createBuyingAndPayment(dto);
        return MessageDto.builder()
                .message(getMessage("isPaySuccess", true, "Status", "결제가 완료되었습니다."))
                .build();
    }


    private void createBuyingAndPayment(PaymentDto dto) {
        Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
        User user = userService.findOneById(dto.getBuyerId());
        Payment payment = dto.toEntity(user, auctionitem, new Money(dto.getPoint())); // 결제 생성
        paymentService.save(payment);  // 결제 저장
        if(!dto.getPoint().equals(BigDecimal.ZERO)){
            userPointService.subtract(user.getId(), new Money(dto.getPoint())); //포인트 차감
        }
        buyingService.save(new InstantBuying(payment));
    }
}



