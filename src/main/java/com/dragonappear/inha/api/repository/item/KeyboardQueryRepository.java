package com.dragonappear.inha.api.repository.item;

import com.dragonappear.inha.api.repository.item.dto.KeyboardApiDto;
import com.dragonappear.inha.api.repository.item.dto.QKeyboardApiDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.dragonappear.inha.domain.item.QItem.item;
import static com.dragonappear.inha.domain.item.product.QKeyboard.keyboard;

@RequiredArgsConstructor
@Repository
public class KeyboardQueryRepository {
    private final JPAQueryFactory queryFactory;

    public KeyboardApiDto findById(Long itemId) {
        KeyboardApiDto dto = queryFactory.select(
                        new QKeyboardApiDto(item.id
                                , item.manufacturer.manufacturerName.stringValue()
                                , item.itemName
                                , item.modelNumber
                                , item.releaseDay
                                , item.releasePrice.amount
                                , item.color
                                , item.likeCount
                                , item.latestPrice.amount
                                , keyboard.length
                                , keyboard.weight
                                , keyboard.keyType
                                , keyboard.type)
                )
                .from(item)
                .join(keyboard).on(keyboard.id.eq(item.id))
                .where(item.id.eq(itemId))
                .fetchOne();
        if (dto == null) {
            throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
        }
        return dto;
    }
}
