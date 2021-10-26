package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.QBuying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.payment.QPayment;
import com.dragonappear.inha.domain.value.Money;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dragonappear.inha.domain.buying.QBuying.*;
import static com.dragonappear.inha.domain.payment.QPayment.payment;

@RequiredArgsConstructor
public class BuyingRepositoryCustomImpl implements BuyingRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Map<Object,Object> findLargestBuyingPrice(Long itemId) {
        Map<Object, Object> map = new HashMap<>();

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
            Payment payment = list.get(0).getPayment();
            map.put("auctionitemId", payment.getAuctionitem().getId());
            map.put("amount",payment.getPaymentPrice().getAmount().toString());
            return map;
        } catch (Exception e) {
            map.put("auctionitemId", "해당 아이템 구매입찰이 존재하지 않습니다");
            map.put("amount", "0");
            return map;
        }
    }


    @NoArgsConstructor
    @Data
    static class BuyingPriceDto {
        private Long auctionitemId;
        private BigDecimal price;

        @Builder
        public BuyingPriceDto(Long auctionitemId, BigDecimal price) {
            this.auctionitemId = auctionitemId;
            this.price = price;
        }
    }
}

