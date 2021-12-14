package com.dragonappear.inha.api.repository.item;

import com.dragonappear.inha.api.repository.item.dto.MonitorApiDto;
import com.dragonappear.inha.api.repository.item.dto.QMonitorApiDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.dragonappear.inha.domain.item.QItem.item;
import static com.dragonappear.inha.domain.item.product.QMonitor.monitor;

@RequiredArgsConstructor
@Repository
public class MonitorQueryRepository {
    private final JPAQueryFactory queryFactory;

    public MonitorApiDto findById(Long itemId) {
        MonitorApiDto dto = queryFactory.select(
                        new QMonitorApiDto(item.id
                                , item.manufacturer.manufacturerName.stringValue()
                                , item.itemName
                                , item.modelNumber
                                , item.releaseDay
                                , item.releasePrice.amount
                                , item.color
                                , item.likeCount
                                , item.latestPrice.amount
                                , monitor.inch
                                , monitor.displayRate
                                , monitor.panelType
                                , monitor.resolution
                                , monitor.dpPort
                                , monitor.hdmi
                                , monitor.maxInjectionRate)
                )
                .from(item)
                .join(monitor).on(monitor.id.eq(item.id))
                .where(item.id.eq(itemId))
                .fetchOne();
        if (dto == null) {
            throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
        }
        return dto;
    }
}
