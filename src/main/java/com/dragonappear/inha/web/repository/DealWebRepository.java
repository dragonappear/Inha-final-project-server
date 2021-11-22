package com.dragonappear.inha.web.repository;


import com.dragonappear.inha.domain.auctionitem.QAuctionitem;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.item.QItem;
import com.dragonappear.inha.domain.selling.QSelling;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.*;
import static com.dragonappear.inha.domain.deal.QDeal.*;
import static com.dragonappear.inha.domain.deal.value.DealStatus.*;
import static com.dragonappear.inha.domain.item.QItem.*;
import static com.dragonappear.inha.domain.selling.QSelling.*;

@RequiredArgsConstructor
@Repository
public class DealWebRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public int getItemDealCount(Long itemId) {
        List<Deal> list = jpaQueryFactory.select(deal)
                .from(deal)
                .join(deal.selling,selling)
                .join(selling.auctionitem,auctionitem)
                .join(auctionitem.item,item)
                .where(item.id.eq(itemId))
                .fetch();
        return list.size();
    }
}
