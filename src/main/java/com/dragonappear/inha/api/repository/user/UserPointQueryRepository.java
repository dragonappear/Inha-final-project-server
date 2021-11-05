package com.dragonappear.inha.api.repository.user;

import com.dragonappear.inha.api.repository.user.dto.MyPageUserPointDto;
import com.dragonappear.inha.api.repository.user.dto.QMyPageUserPointDto;
import com.dragonappear.inha.domain.user.QUser;
import com.dragonappear.inha.domain.value.Money;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

import static com.dragonappear.inha.domain.user.QUser.*;
import static com.dragonappear.inha.domain.user.QUserPoint.userPoint;


@RequiredArgsConstructor
@Repository
public class UserPointQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<MyPageUserPointDto> getMyPageUserPointDto(Long userId) {
        /*StringExpression pointType = new CaseBuilder()
                .when(userPoint.earned.amount.gt(Money.wons(0).getAmount()))
                .then("적립")
                .otherwise("사용");*/

        Expression<BigDecimal> point = new CaseBuilder()
                .when(userPoint.earned.amount.gt(Money.wons(0).getAmount()))
                .then(userPoint.earned.amount)
                .otherwise(userPoint.used.amount.multiply(-1));

        return jpaQueryFactory.select(new QMyPageUserPointDto(userPoint.pointStatus, userPoint.createdDate, point))
                .from(userPoint)
                .join(userPoint.user, user)
                .where(userPoint.user.id.eq(userId))
                .orderBy(userPoint.createdDate.desc())
                .fetch();
    }


}
