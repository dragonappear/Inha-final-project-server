package com.dragonappear.inha.api.repository.buying;

import com.dragonappear.inha.api.repository.buying.dto.*;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.QBidBuying;
import com.dragonappear.inha.domain.buying.QBuying;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.buying.QBidBuying.*;
import static com.dragonappear.inha.domain.buying.QBuying.buying;
import static com.dragonappear.inha.domain.buying.value.BuyingStatus.*;
import static com.dragonappear.inha.domain.deal.QDeal.deal;
import static com.dragonappear.inha.domain.item.QItemImage.itemImage1;
import static com.dragonappear.inha.domain.payment.QPayment.payment;


@RequiredArgsConstructor
@Repository
public class BuyingQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public MyPageUserBuyingSimpleDto getMyPageUserBuyingSimpleDto(Long userId){
        List<Buying> results = jpaQueryFactory.selectFrom(buying)
                .join(buying.payment, payment).on(buying.payment.id.eq(payment.id))
                .where(payment.user.id.eq(userId))
                .fetch();

        MyPageUserBuyingSimpleDto dto = new MyPageUserBuyingSimpleDto();
        results.stream().forEach(r ->{
            if(r.getBuyingStatus()== 구매입찰중){
                dto.countBidding();
            }
            else if(r.getBuyingStatus()== 거래중 || r.getBuyingStatus()==구매완료){
                dto.countOngoing();
            }
            else{
                dto.countEnd();
            }
            dto.countTotal();
        });
        return dto;
    }

    public List<MyPageUserBuyingBidDto> getMyPageUserBuyingBidDto(Long userId) {

        return jpaQueryFactory.select(new QMyPageUserBuyingBidDto(itemImage1.itemImage.fileOriName
                        , payment.item.itemName
                        , payment.paymentPrice.amount
                        , bidBuying.endDate))
                .from(bidBuying)
                .join(payment).on(bidBuying.payment.id.eq(payment.id))
                .join(itemImage1).on(itemImage1.item.id.eq(payment.item.id))
                .where(bidBuying.buyingStatus.eq(구매입찰중).and(payment.user.id.eq(userId)))
                .fetch();
    }

   public  List<MyPageUserBuyingOngoingDto> getMyPageUserBuyingOngoingDto(Long userId) {
       return jpaQueryFactory.select(new QMyPageUserBuyingOngoingDto(buying.id
                       , itemImage1.itemImage.fileOriName
                       , payment.auctionitem.item.itemName
                       , buying.buyingStatus))
               .from(buying)
               .join(buying.payment, payment).on(payment.user.id.eq(userId))
               .join(itemImage1).on(itemImage1.item.id.eq(payment.item.id))
               .where(buying.buyingStatus.in(거래중, 구매완료))
               .fetch();
   }

    public List<MyPageUserBuyingEndDto> getMyPageUserBuyingEndDto(Long userId) {
        return jpaQueryFactory.select(new QMyPageUserBuyingEndDto(buying.id
                        ,itemImage1.itemImage.fileOriName
                        ,payment.auctionitem.item.itemName
                        ,payment.createdDate
                        ,buying.buyingStatus))
                .from(buying)
                .join(buying.deal,deal).on(deal.buying.id.eq(buying.id))
                .join(buying.payment, payment).on(buying.payment.id.eq(payment.id).and(payment.user.id.eq(userId)))
                .join(itemImage1).on(itemImage1.item.id.eq(payment.auctionitem.item.id))
                .where(buying.buyingStatus.in(구매입찰종료, 구매취소, 배송완료))
                .fetch();
    }
}
