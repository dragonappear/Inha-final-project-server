package com.dragonappear.inha.api.repository.auctionitem;

import com.dragonappear.inha.domain.auctionitem.QAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.*;
import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.*;
import static com.dragonappear.inha.domain.item.QItem.*;

@RequiredArgsConstructor
@Repository
public class AuctionitemApiRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Map<Object, Object> findBidAuctionitems(Long itemId) {
        Map<Object, Object> map = new HashMap<>();
        List<AuctionitemDto> lists = jpaQueryFactory.select(new QAuctionitemDto(
                        auctionitem.id,
                        auctionitem.price.amount
                ))
                .from(auctionitem)
                .join(auctionitem.item, item)
                .where(auctionitem.auctionitemStatus.eq(경매중).and(item.id.eq(itemId)))
                .orderBy(auctionitem.price.amount.asc())
                .fetch();

        System.out.println("lists.size() = " + lists.size());
        List<Map<Object, Object>> list = lists.stream().map(dto -> {
            Map<Object, Object> tmp = new HashMap<>();
            tmp.put("auctionitemId", dto.getId());
            tmp.put("amount", dto.getAmount());
            return tmp;
        }).collect(Collectors.toList());

        map.put("content", list);
        return map;
    }
}
