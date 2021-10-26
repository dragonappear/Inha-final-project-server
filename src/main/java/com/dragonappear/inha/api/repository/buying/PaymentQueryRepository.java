package com.dragonappear.inha.api.repository.buying;

import com.dragonappear.inha.domain.deal.QDeal;
import com.dragonappear.inha.domain.payment.QPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.payment.QPayment.*;

@RequiredArgsConstructor
@Repository
public class PaymentQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


    /*public List<Object> getAllPayments() {
        jpaQueryFactory.selectFrom(payment)
                .join(QDeal.deal.)
    }*/
}
