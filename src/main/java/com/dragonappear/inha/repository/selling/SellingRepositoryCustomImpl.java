package com.dragonappear.inha.repository.selling;


import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.selling.Selling;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.auctionitem;
import static com.dragonappear.inha.domain.selling.QBidSelling.*;
import static com.dragonappear.inha.domain.selling.QSelling.*;
import static com.dragonappear.inha.domain.selling.value.SellingStatus.*;

@RequiredArgsConstructor
public class SellingRepositoryCustomImpl implements SellingRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Map<Object,Object> findLowestSellingPrice(Long itemId) {
        Map<Object, Object> map = new HashMap<>();

        List<Selling> list = jpaQueryFactory.selectFrom(selling)
                .leftJoin(selling.auctionitem, auctionitem)
                .where(selling.sellingStatus.eq(판매입찰중).and(auctionitem.item.id.eq(itemId)))
                .orderBy(auctionitem.price.amount.asc())
                .fetch();

        try{
            if(list.size()==0){
                throw new Exception();
            }
            Auctionitem auctionitem = list.get(0).getAuctionitem();
            map.put("sellingId", list.get(0).getId());
            map.put("amount",auctionitem.getPrice().getAmount());
            return map;
        }catch (Exception e){
            map.put("sellingId", "해당 아이템 판매입찰이 존재하지 않습니다");
            map.put("amount", 0);
            return map;
        }
    }


    @Override
    public Long endBidSelling() {
        return jpaQueryFactory.update(bidSelling)
                .where(bidSelling.sellingStatus.eq(판매입찰중).and(bidSelling.endDate.before(LocalDateTime.now())))
                .set(bidSelling.sellingStatus, 판매입찰종료)
                .execute();
    }
}
