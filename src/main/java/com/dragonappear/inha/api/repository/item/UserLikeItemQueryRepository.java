package com.dragonappear.inha.api.repository.item;

import com.dragonappear.inha.api.repository.item.dto.MyPageUserLikeItemDto;
import com.dragonappear.inha.api.repository.item.dto.QMyPageUserLikeItemDto;
import com.dragonappear.inha.domain.item.ItemImage;
import com.dragonappear.inha.domain.item.QItem;
import com.dragonappear.inha.domain.item.QItemImage;
import com.dragonappear.inha.domain.item.QUserLikeItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.item.QItem.*;
import static com.dragonappear.inha.domain.item.QItemImage.*;
import static com.dragonappear.inha.domain.item.QUserLikeItem.*;


@RequiredArgsConstructor
@Repository
public class UserLikeItemQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<MyPageUserLikeItemDto> getMyPageUserLikeItemDtos(Long userId) {
        return jpaQueryFactory.select(
                        new QMyPageUserLikeItemDto(itemImage1.itemImage.fileOriName
                                , item.manufacturer.manufacturerName
                                , item.itemName
                                , item.latestPrice.amount))
                .from(userLikeItem)
                .join(userLikeItem.item, item).on(userLikeItem.item.id.eq(item.id))
                .join(itemImage1).on(itemImage1.item.id.eq(userLikeItem.item.id))
                .where(userLikeItem.user.id.eq(userId))
                .fetch();
    }

}
