package com.dragonappear.inha.repository.selling;


import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.QAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.payment.QBidPayment;
import com.dragonappear.inha.domain.selling.QSelling;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.item.ItemService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.auctionitem;
import static com.dragonappear.inha.domain.selling.QSelling.*;
import static com.dragonappear.inha.domain.selling.value.SellingStatus.*;

@RequiredArgsConstructor
public class SellingRepositoryCustomImpl implements SellingRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Selling> findByStatus(SellingStatus sellingStatus) {
        return jpaQueryFactory.selectFrom(selling)
                .where(selling.sellingStatus.eq(판매입찰중))
                .orderBy(selling.auctionitem.endDate.asc())
                .fetch();
    }

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
            map.put("auctionitemId", "해당 아이템 판매입찰이 존재하지 않습니다");
            map.put("amount", 0);
            return map;
        }
    }


    @Override
    public Long endBidSelling() {
        List<Long> list = jpaQueryFactory.selectFrom(auctionitem)
                .where(auctionitem.endDate.before((LocalDateTime.now())))
                .fetch().stream().map(item -> item.getId()).collect(Collectors.toList());

        jpaQueryFactory.update(auctionitem)
                .where(auctionitem.endDate.before(LocalDateTime.now()))
                .set(auctionitem.auctionitemStatus, AuctionitemStatus.경매기한만료)
                .execute();

        return jpaQueryFactory.update(selling)
                .where(selling.sellingStatus.eq(판매입찰중).and(selling.auctionitem.id.in(list)))
                .set(selling.sellingStatus, 판매입찰종료)
                .execute();
    }
}
