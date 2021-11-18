package com.dragonappear.inha.repository.deal;

import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.inspection.QInspection;
import com.dragonappear.inha.domain.selling.SellingDelivery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.dragonappear.inha.domain.deal.QDeal.*;
import static com.dragonappear.inha.domain.deal.value.DealStatus.*;
import static com.dragonappear.inha.domain.inspection.QInspection.*;
import static com.dragonappear.inha.domain.selling.QSelling.*;

@RequiredArgsConstructor
public class DealRepositoryCustomImpl implements DealRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Deal> findUnregisteredSellingDelivery() {
        return jpaQueryFactory.selectFrom(deal)
                .join(deal.selling, selling)
                .where(deal.dealStatus.eq(거래진행)
                        .and(deal.createdDate.before(LocalDateTime.now().minusDays(2)))
                        .and(selling.sellingDelivery.isNull()))
                .fetch();
    }

    @Override
    public List<Deal> findUndeliveredDeal() {
        return jpaQueryFactory.selectFrom(deal)
                .where(deal.dealStatus.eq(판매자발송완료)
                        .and(deal.updatedDate.before(LocalDateTime.now().minusDays(7))))
                .fetch();
    }

    @Override
    public List<Deal> findFailInspectionDeal() {
        return jpaQueryFactory.selectFrom(deal)
                .where(deal.dealStatus.eq(검수탈락)
                        .and(deal.updatedDate.before(LocalDateTime.now().minusDays(2))))
                .fetch();
    }
}
