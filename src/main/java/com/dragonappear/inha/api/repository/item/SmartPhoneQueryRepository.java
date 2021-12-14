package com.dragonappear.inha.api.repository.item;

import com.dragonappear.inha.api.repository.item.dto.QSmartPhoneApiDto;
import com.dragonappear.inha.api.repository.item.dto.SmartPhoneApiDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.dragonappear.inha.domain.item.QItem.item;
import static com.dragonappear.inha.domain.item.product.QSmartPhone.smartPhone;

@RequiredArgsConstructor
@Repository
public class SmartPhoneQueryRepository {
    private final JPAQueryFactory queryFactory;

    public SmartPhoneApiDto findById(Long itemId) {
        SmartPhoneApiDto dto = queryFactory.select(
                        new QSmartPhoneApiDto(item.id
                                , item.manufacturer.manufacturerName.stringValue()
                                , item.itemName
                                , item.modelNumber
                                , item.releaseDay
                                , item.releasePrice.amount
                                , item.color
                                , item.likeCount
                                , item.latestPrice.amount
                                , smartPhone.inch
                                , smartPhone.cpu
                                , smartPhone.core
                                , smartPhone.memory
                                , smartPhone.storage
                                , smartPhone.gpu
                                , smartPhone.weight
                                , smartPhone.os
                                , smartPhone.apType
                                , smartPhone.ppi
                                , smartPhone.maxInjectionRate)
                )
                .from(item)
                .join(smartPhone).on(smartPhone.id.eq(item.id))
                .where(item.id.eq(itemId))
                .fetchOne();
        if (dto == null) {
            throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
        }
        return dto;
    }
}
