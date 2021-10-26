package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.QBuying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.payment.QPayment;
import com.dragonappear.inha.domain.value.Money;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.buying.QBuying.*;
import static com.dragonappear.inha.domain.payment.QPayment.payment;

@RequiredArgsConstructor
public class BuyingRepositoryCustomImpl implements BuyingRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Money findLargestBuyingPrice(Long itemId) {
        List<Buying> list = jpaQueryFactory.selectFrom(buying)
                .leftJoin(buying.payment, payment)
                .where(buying.buyingStatus.eq(BuyingStatus.구매입찰중)
                        .and(payment.auctionitem.item.id.eq(itemId)))
                .orderBy(payment.paymentPrice.amount.desc())
                .fetch();
        try {
            if(list.size()==0){
                throw new Exception();
            }
            return list.get(0).getPayment().getPaymentPrice();
        } catch (Exception e) {
            return Money.wons(0L);
        }
    }
}
