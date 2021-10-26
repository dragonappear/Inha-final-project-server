package com.dragonappear.inha.repository.selling;


import com.dragonappear.inha.domain.selling.QSelling;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.item.ItemService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.auctionitem;
import static com.dragonappear.inha.domain.selling.QSelling.*;

@RequiredArgsConstructor
public class SellingRepositoryCustomImpl implements SellingRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Money findLowestSellingPrice(Long itemId) {
        List<Selling> list = jpaQueryFactory.selectFrom(selling)
                .leftJoin(selling.auctionitem, auctionitem)
                .where(selling.sellingStatus.eq(SellingStatus.판매입찰중).and(auctionitem.item.id.eq(itemId)))
                .orderBy(auctionitem.price.amount.asc())
                .fetch();
        try{
            if(list.size()==0){
                throw new Exception();
            }
            return list.get(0).getAuctionitem().getPrice();
        }catch (Exception e){
            return Money.wons(0L);
        }
    }
}
