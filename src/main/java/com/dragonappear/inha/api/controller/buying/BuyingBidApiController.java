package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.api.controller.buying.dto.BidPaymentDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.config.iamport.CancelDto;
import com.dragonappear.inha.config.iamport.IamportConfig;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.deal.DealService;
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

import static com.dragonappear.inha.api.controller.buying.validation.PaymentValidation.validationPayment;
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
    private final SellingService sellingService;
    private final DealService dealService;

    @ApiOperation(value = "입찰구매 저장 API", notes = "입찰구매 저장")
    @PostMapping("/payments/new/bid")
    public MessageDto postBidPayment(@RequestBody BidPaymentDto dto) {
        // 결제내역 검증
        MessageDto result = validationPayment(dto,auctionItemService,userService,userPointService,paymentService);
        if (result.getMessage().get("isValidateSuccess").equals(false)) {
            return MessageDto.builder()
                    .message(getMessage("isPaySuccess", false, "Status", result.getMessage().get("Status").toString()))
                    .build();
        }

        try {
            if (createDeal(dto)) { // 결제 생성
                return MessageDto.builder()
                        .message(getMessage("isPaySuccess", true, "Status", "결제가 완료되었습니다."))
                        .build();
            }else{
                if (IamportConfig.cancelPayment(CancelDto.getCancelDto(dto)) == 1) { // 결제 취소
                    return MessageDto.builder()
                            .message(getMessage("isPaySuccess", false, "Status", "결제를 취소하였습니다.")).build();
                } else {
                    return MessageDto.builder()
                            .message(getMessage("isPaySuccess", false, "Status", "결제취소가 완료되지 않았습니다.")).build();
                }
            }
        } catch (Exception e) {
            return MessageDto.builder()
                    .message(getMessage("isPaySuccess", false, "Status", e.getMessage())).build();
        }
    }

    private boolean createDeal(BidPaymentDto dto) throws Exception{
        Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
        User user = userService.findOneById(dto.getBuyerId());
        Payment payment = dto.toEntity(user, auctionitem, new Money(dto.getPoint())); // 결제 생성
        paymentService.save(payment);  // 결제 저장
        if(!dto.getPoint().equals(BigDecimal.ZERO)){
                userPointService.subtract(user.getId(), new Money(dto.getPoint())); //포인트 차감
        }
        Buying buying = new BidBuying(payment, dto.getEndDate()); // 구매 생성
        buyingService.save(buying);  // 구매 저장
        Selling selling = sellingService.findByAuctionitemId(dto.getAuctionitemId()); // 판매 조회
        Deal deal = new Deal(buying, selling); // 거래 생성
        dealService.save(deal); // 거래 저장
        return true;
    }
}
