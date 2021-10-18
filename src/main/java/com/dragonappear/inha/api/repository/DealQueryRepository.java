package com.dragonappear.inha.api.repository;

import com.dragonappear.inha.domain.deal.QDeal;
import com.dragonappear.inha.domain.selling.QSelling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.value.Money;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.deal.QDeal.*;
import static com.dragonappear.inha.domain.selling.QSelling.*;
import static com.dragonappear.inha.domain.selling.value.SellingStatus.*;

@RequiredArgsConstructor
@Repository
public class DealQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<BigDecimal> findRecentPrice(Long itemId, Pageable pageable) {
        return queryFactory.select(selling.auctionitem.price)
                .from(deal)
                .leftJoin(deal.selling, selling)
                .where(selling.auctionitem.item.id.eq(itemId).and(selling.sellingStatus.eq(판매완료)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream().map(money -> money.getAmount()).collect(Collectors.toList());
    }
}
