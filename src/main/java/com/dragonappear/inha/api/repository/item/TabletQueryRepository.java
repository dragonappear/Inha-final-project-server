package com.dragonappear.inha.api.repository.item;

import com.dragonappear.inha.api.repository.item.dto.QTabletApiDto;
import com.dragonappear.inha.api.repository.item.dto.TabletApiDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.dragonappear.inha.domain.item.QItem.item;
import static com.dragonappear.inha.domain.item.product.QTablet.tablet;

@RequiredArgsConstructor
@Repository
public class TabletQueryRepository {
    private final JPAQueryFactory queryFactory;

    public TabletApiDto findById(Long itemId) {
        TabletApiDto dto = queryFactory.select(
                        new QTabletApiDto(item.id
                                , item.manufacturer.manufacturerName.stringValue()
                                , item.itemName
                                , item.modelNumber
                                , item.releaseDay
                                , item.releasePrice.amount
                                , item.color
                                , item.likeCount
                                , item.latestPrice.amount
                                , tablet.inch
                                , tablet.cpu
                                , tablet.core
                                , tablet.os
                                , tablet.memory
                                , tablet.storage
                                , tablet.gpu
                                , tablet.weight
                                , tablet.ppi
                                , tablet.maxInjectionRate)
                )
                .from(item)
                .join(tablet).on(tablet.id.eq(item.id))
                .where(item.id.eq(itemId))
                .fetchOne();
        if (dto == null) {
            throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
        }
        return dto;
    }
}
