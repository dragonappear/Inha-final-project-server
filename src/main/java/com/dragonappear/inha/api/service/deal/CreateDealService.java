package com.dragonappear.inha.api.service.deal;


import com.dragonappear.inha.api.controller.buying.dto.BidPaymentApiDto;
import com.dragonappear.inha.api.controller.buying.dto.InstantPaymentApiDto;
import com.dragonappear.inha.api.controller.selling.dto.InstantSellingDto;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import com.dragonappear.inha.api.controller.selling.dto.BidSellingDto;
import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.api.service.firebase.FirebaseCloudMessageService;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.InstantBuying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.exception.selling.SellingException;
import com.dragonappear.inha.exception.deal.DealException;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.item.ItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.selling.SellingService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import com.dragonappear.inha.service.user.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CreateDealService {
    private final PaymentService paymentService;
    private final UserService userService;
    private final UserPointService userPointService;
    private final BuyingService buyingService;
    private final SellingService sellingService;
    private final DealService dealService;
    private final ItemService itemService;
    private final AuctionItemService auctionItemService;
    private final FcmSendService fcmSendService;

    @Transactional
    public Long createInstantBuying(InstantPaymentApiDto dto) throws DealException {
        try {
            Selling selling = sellingService.findBySellingId(dto.getSellingId());
            Auctionitem auctionitem = selling.getAuctionitem();
            User user = userService.findOneById(dto.getBuyerId());
            if(!dto.getPoint().equals(BigDecimal.ZERO)){ //????????? ??????
                userPointService.subtract(user.getId(), new Money(dto.getPoint()));
            }
            Payment payment = dto.toEntity(user, auctionitem, new Money(dto.getPoint())); // ?????? ??????
            paymentService.save(payment);
            Buying buying = new InstantBuying(payment); // ?????? ??????
            buyingService.save(buying);
            Deal deal = new Deal(buying, selling); // ?????? ??????
            Long dealId = dealService.save(deal);
            fcmToSeller(selling);
            auctionItemService.updateItemLatestPrice(auctionitem.getItem(),auctionitem.getPrice());
            return dealId;
        } catch (Exception e) {
            throw DealException.builder()
                    .message(e.getMessage())
                    .paymentApiDto(dto)
                    .cancelDto(CancelDto.getCancelDto(dto))
                    .build();
        }
    }


    @Transactional
    public void createBidBuying(BidPaymentApiDto dto) throws DealException{
        try {
            User user = userService.findOneById(dto.getBuyerId());
            Item item = itemService.findByItemId(dto.getItemId());
            if (!dto.getPoint().equals(BigDecimal.ZERO)) {
                userPointService.subtract(user.getId(), new Money(dto.getPoint())); //????????? ??????
            }
            Payment payment = dto.toEntity(user,item,new Money(dto.getPoint())); // ?????? ??????
            paymentService.save(payment);
            Buying buying = new BidBuying(payment, dto.getEndDate()); // ?????? ??????
            buyingService.save(buying);
        } catch (Exception e) {
            throw DealException.builder()
                    .message(e.getMessage())
                    .paymentApiDto(dto)
                    .cancelDto(CancelDto.getCancelDto(dto))
                    .build();
        }
    }

    @Transactional
    public void createBidSelling(BidSellingDto dto) {
        try {
            User user = userService.findOneById(dto.getUserId());
            Item item = itemService.findByItemId(dto.getItemId());
            BigDecimal price = dto.getPrice();
            Long bidSave = auctionItemService.save(item, new Money(price));
            Auctionitem auctionitem = auctionItemService.findById(bidSave);
            sellingService.bidSave(user, auctionitem,dto.getEndDate());
        } catch (Exception e) {
            throw new SellingException(e.getMessage());
        }
    }

    @Transactional
    public Long createInstantSelling(InstantSellingDto dto) {
        try {
            User user = userService.findOneById(dto.getUserId());
            Buying buying = buyingService.findById(dto.getBuyingId());
            if(buying.getBuyingStatus()!= BuyingStatus.???????????????){
                throw new IllegalStateException("?????? ?????????????????? ?????? ??????????????????.");
            }
            Item item = itemService.findByItemId(dto.getItemId());
            BigDecimal price = dto.getPrice();
            Long instantSave = auctionItemService.save(item, new Money(price));
            Auctionitem auctionitem = auctionItemService.findById(instantSave);
            buying.getPayment().updateAuctionitem(auctionitem);
            Long auctionitemId = sellingService.instantSave(user, auctionitem);
            Selling selling = sellingService.findBySellingId(auctionitemId);
            Long dealId = dealService.save(new Deal(buying, selling));
            fcmToBuyer(buying);
            auctionItemService.updateItemLatestPrice(item, new Money(price));
            return dealId;
        } catch (Exception e) {
            throw new SellingException(e.getMessage());
        }
    }

    private void fcmToBuyer(Buying buying) throws IOException {
        User buyer = buying.getPayment().getUser();
        String title = "?????????????????? ???????????? ????????? ?????????????????????.";
        String body = buying.getPayment().getAuctionitem().getItem().getItemName() + " ????????? ?????????????????????.\n"+
                "???????????? ?????????????????? ???????????? ???????????? ???????????? ?????? ??????????????????.";
        try {
            fcmSendService.sendFCM(buyer, title,body);
        } catch (Exception e) {
            log.error("buyerId:{} ???????????? FCM ???????????? ???????????? ???????????????.",buyer.getId());
        }
    }

    private void fcmToSeller(Selling selling) throws IOException {
        User seller = selling.getSeller();
        String title = "?????????????????? ???????????? ????????? ?????????????????????.";
        String body = selling.getAuctionitem().getItem().getItemName() + " ????????? ?????????????????????.\n" +
                "2????????? ?????????????????? ????????? ?????? ???????????? ????????? ??? ?????????????????? ???????????? ???, ??????????????? ???????????? ?????????????????? ??????????????? ??????????????? ????????????.\n" +
                "????????? ????????? ????????? ????????? ?????? ????????? ????????? ??? ????????? ?????? ??????????????????.\n";
        try {
            fcmSendService.sendFCM(seller, title, body);
        } catch (Exception e) {
            log.error("sellerId:{} ???????????? FCM ???????????? ???????????? ???????????????.",seller.getId());
        }
    }
}
