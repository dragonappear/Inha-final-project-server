package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.QBuying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.value.Money;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.dragonappear.inha.domain.buying.QBuying.*;

@RequiredArgsConstructor
@Repository
public class BuyingRepositoryCustomImpl implements BuyingRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Money findLargestBuyingPrice(Long itemId) {
        Buying buying = jpaQueryFactory.selectFrom(QBuying.buying)
                .where(QBuying.buying.buyingStatus.eq(BuyingStatus.구매입찰중).and(QBuying.buying.payment.auctionitem.item.id.eq(itemId)))
                .orderBy(QBuying.buying.payment.paymentPrice.amount.desc())
                .fetchOne();
        try {
            return buying.getPayment().getPaymentPrice();
        } catch (Exception e) {
            return Money.wons(0L);
        }
    }
}
