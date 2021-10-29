package com.dragonappear.inha.api.repository.auctionitem;

import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.*;
import static com.dragonappear.inha.domain.item.QItem.*;
import static com.dragonappear.inha.domain.selling.QSelling.*;

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
                .join(auctionitem.selling, selling)
                .where(selling.sellingStatus.eq(SellingStatus.판매입찰중).and(item.id.eq(itemId)))
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
