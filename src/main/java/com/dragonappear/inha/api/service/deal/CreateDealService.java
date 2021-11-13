package com.dragonappear.inha.api.service.deal;


import com.dragonappear.inha.api.controller.buying.dto.BidPaymentApiDto;
import com.dragonappear.inha.api.controller.buying.dto.InstantPaymentApiDto;
import com.dragonappear.inha.api.controller.selling.dto.InstantSellingDto;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import com.dragonappear.inha.api.controller.selling.dto.BidSellingDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

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
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final UserTokenService userTokenService;

    @Transactional
    public Long createInstantBuying(InstantPaymentApiDto dto) throws DealException {
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
            Long dealId = dealService.save(deal);
            fcmToSeller(selling, auctionitem);
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
                userPointService.subtract(user.getId(), new Money(dto.getPoint())); //포인트 차감
            }
            Payment payment = dto.toEntity(user,item,new Money(dto.getPoint())); // 결제 생성
            paymentService.save(payment);
            Buying buying = new BidBuying(payment, dto.getEndDate()); // 구매 생성
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
            if(buying.getBuyingStatus()!= BuyingStatus.구매입찰중){
                throw new IllegalStateException("해당 구매아이디는 이미 거래중입니다.");
            }
            Item item = itemService.findByItemId(dto.getItemId());
            BigDecimal price = dto.getPrice();
            Long instantSave = auctionItemService.save(item, new Money(price));
            Auctionitem auctionitem = auctionItemService.findById(instantSave);
            buying.getPayment().updateAuctionitem(auctionitem);
            Long auctionitemId = sellingService.instantSave(user, auctionitem);
            Selling selling = sellingService.findBySellingId(auctionitemId);
            Long dealId = dealService.save(new Deal(buying, selling));
            fcmToBuyer(buying, item);
            auctionItemService.updateItemLatestPrice(item, new Money(price));
            return dealId;
        } catch (Exception e) {
            throw new SellingException(e.getMessage());
        }
    }

    private void fcmToBuyer(Buying buying, Item item) throws IOException {
        String token = userTokenService.findTokenByUserIdAndType(buying.getPayment().getUser().getId(), "fcm");
        String title = "입찰판매하신 아이템의 거래가 성사되었습니다.";
        String body = item.getItemName() + " 거래가 성사되었습니다.\n" +
                "2일내로 검수지역으로 물건과 구매 영수증을 동봉한 후 안전포장하여 배송하신 후, 마이페이지 진행중인 판매내역에서 송장번호를 등록하시기 바랍니다.\n" +
                "파손된 상태로 제품이 배송될 시에 거래가 취소될 수 있음을 미리 알려드립니다.\n";
        firebaseCloudMessageService.sendMessageTo(token,title,body);
    }

    private void fcmToSeller(Selling selling, Auctionitem auctionitem) throws IOException {
        String token = userTokenService.findTokenByUserIdAndType(selling.getSeller().getId(), "fcm");
        String title = "입찰구매하신 아이템의 거래가 성사되었습니다.";
        String body = auctionitem.getItem().getItemName() + " 거래가 성사되었습니다.\n"+
                "판매자가 검수지역으로 미배송시 자동결제 취소됨을 미리 알려드립니다.";
        firebaseCloudMessageService.sendMessageTo(token,title,body);
    }
}
