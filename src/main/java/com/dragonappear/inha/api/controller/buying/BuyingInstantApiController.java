package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.buying.dto.InstantPaymentDto;
import com.dragonappear.inha.api.controller.buying.iamport.CancelDto;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.InstantBuying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.exception.deal.DealException;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.item.ItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.selling.SellingService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.dragonappear.inha.api.controller.buying.ValidatePayment.validate;
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
    private final SellingService sellingService;
    private final DealService dealService;
    private final ItemService itemService;


    @ApiOperation(value = "즉시구매 결제 저장 API", notes = "즉시구매 결제 저장")
    @PostMapping("/payments/new/instant")
    public MessageDto postInstantPayment(@RequestBody InstantPaymentDto dto) {
        // 결제내역 검증
        validate(dto,itemService,auctionItemService,userService,userPointService,paymentService,sellingService);
        createDeal(dto);
        return MessageDto.builder()
                .message(getMessage("isPaySuccess", true, "Status", "결제가 완료되었습니다."))
                .build();
    }

    private void createDeal(InstantPaymentDto dto) throws DealException {
        try {
            Selling selling = sellingService.findBySellingId(dto.getSellingId());
            Auctionitem auctionitem = selling.getAuctionitem();
            User user = userService.findOneById(dto.getBuyerId());
            if(!dto.getPoint().equals(BigDecimal.ZERO)){ //포인트 차감
                userPointService.subtract(user.getId(), new Money(dto.getPoint()));
            }
            Payment payment = dto.toEntity(user, auctionitem, new Money(dto.getPoint())); // 결제 생성
            paymentService.save(payment);
            Buying buying = new InstantBuying(payment); // 구매 생성
            buyingService.save(buying);
            Deal deal = new Deal(buying, selling); // 거래 생성
            dealService.save(deal);
        } catch (Exception e) {
            throw DealException.builder()
                    .message(e.getMessage())
                    .paymentDto(dto)
                    .cancelDto(CancelDto.getCancelDto(dto))
                    .build();
        }
    }
}




