package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.payment.Payment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dragonappear.inha.domain.buying.QBidBuying.*;
import static com.dragonappear.inha.domain.buying.QBuying.*;
import static com.dragonappear.inha.domain.buying.value.BuyingStatus.*;
import static com.dragonappear.inha.domain.payment.QPayment.*;
import static com.dragonappear.inha.domain.payment.value.PaymentStatus.*;


@RequiredArgsConstructor
public class BuyingRepositoryCustomImpl implements BuyingRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Map<Object,Object> findLargestBuyingPrice(Long itemId) {
        Map<Object, Object> map = new HashMap<>();
        List<BidBuying> list = jpaQueryFactory.selectFrom(bidBuying)
                .join(payment).on(bidBuying.payment.id.eq(payment.id))
                .where(bidBuying.buyingStatus.eq(구매입찰중)
                        .and(payment.item.id.eq(itemId)))
                .orderBy(payment.paymentPrice.amount.desc())
                .fetch();
        try {
            if(list.size()==0){
                throw new Exception();
            }
            Payment payment = list.get(0).getPayment();
            map.put("buyingId", payment.getBuying().getId());
            map.put("amount",payment.getPaymentPrice().getAmount());
            return map;
        } catch (Exception e) {
            map.put("buyingId", "해당 아이템 구매입찰이 존재하지 않습니다");
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

    @Override
    public Long endBidBuying() {
        return jpaQueryFactory.update(bidBuying)
                .where(bidBuying.buyingStatus.eq(구매입찰중).and(bidBuying.endDate.before(LocalDateTime.now())))
                .set(bidBuying.buyingStatus, 구매입찰종료)
                .execute();
    }

    @Override
    public List<Buying> findOverdueAndNotCanceled() {
        return  jpaQueryFactory.selectFrom(buying)
                .join(buying.payment, payment)
                .where(buying.buyingStatus.eq(구매입찰종료).and(payment.paymentStatus.eq(결제완료)))
                .fetch();
    }
}

