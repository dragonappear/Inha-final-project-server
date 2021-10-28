package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.api.controller.buying.dto.BidPaymentDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.buying.iamport.CancelDto;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.item.Item;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static com.dragonappear.inha.api.controller.buying.ValidatePayment.validate;
import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"입찰구매 API"})
@RestController
@RequiredArgsConstructor
public class BuyingBidApiController {
    private final PaymentService paymentService;
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    private final UserPointService userPointService;
    private final BuyingService buyingService;
    private final ItemService itemService;
    private final SellingService sellingService;


    @ApiOperation(value = "입찰구매 저장 API", notes = "입찰구매 저장")
    @PostMapping("/payments/new/bid")
    public MessageDto postBidPayment(@RequestBody BidPaymentDto dto) {
        // 결제내역 검증
        validate(dto,itemService,auctionItemService,userService,userPointService,paymentService,sellingService);
        createDeal(dto);
        return MessageDto.builder()
                .message(getMessage("isPaySuccess", true, "Status", "결제가 완료되었습니다."))
                .build();
    }

    private void createDeal(BidPaymentDto dto) throws DealException{
        try {
            User user = userService.findOneById(dto.getBuyerId());
            Item item = itemService.findByItemId(dto.getItemId());
            if (!dto.getPoint().equals(BigDecimal.ZERO)) {
                userPointService.subtract(user.getId(), new Money(dto.getPoint())); //포인트 차감
            }
            Payment payment = dto.toEntity(user,item,new Money(dto.getPoint())); // 결제 생성
            paymentService.save(payment);
            Buying buying = new BidBuying(payment, dto.getEndDate()); // 구매 생성
            buyingService.save(buying);
        } catch (Exception e) {
            throw DealException.builder()
                    .message(e.getMessage())
                    .paymentDto(dto)
                    .cancelDto(CancelDto.getCancelDto(dto))
                    .build();
        }
    }
}
