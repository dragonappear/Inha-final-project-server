package com.dragonappear.inha.repository.buying;



import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.payment.Payment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dragonappear.inha.domain.buying.QBidBuying.*;
import static com.dragonappear.inha.domain.payment.QBidPayment.*;


@RequiredArgsConstructor
public class BuyingRepositoryCustomImpl implements BuyingRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Map<Object,Object> findLargestBuyingPrice(Long itemId) {
        Map<Object, Object> map = new HashMap<>();
        List<BidBuying> list = jpaQueryFactory.selectFrom(bidBuying)
                .join(bidPayment).on(bidBuying.payment.id.eq(bidPayment.id))
                .where(bidBuying.buyingStatus.eq(BuyingStatus.구매입찰중)
                        .and(bidPayment.item.id.eq(itemId)))
                .orderBy(bidPayment.paymentPrice.amount.desc())
                .fetch();

        System.out.println("list = " + list);
        try {
            if(list.size()==0){
                throw new Exception();
            }
            Payment payment = list.get(0).getPayment();
            map.put("buyingId", payment.getBuying().getId());
            map.put("amount",payment.getPaymentPrice().getAmount());
            return map;
        } catch (Exception e) {
            map.put("auctionitemId", "해당 아이템 구매입찰이 존재하지 않습니다");
            map.put("amount", 0);
            return map;
        }
    }

    @Override
    public List<BidBuying> findByStatus(BuyingStatus buyingStatus) {
        return jpaQueryFactory.selectFrom(bidBuying)
                .where(bidBuying.buyingStatus.eq(buyingStatus))
                .orderBy(bidBuying.endDate.asc())
                .fetch();
    }

}

