package com.dragonappear.inha.api.repository.buying;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
