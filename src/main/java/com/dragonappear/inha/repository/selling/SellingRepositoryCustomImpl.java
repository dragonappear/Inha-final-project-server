package com.dragonappear.inha.repository.selling;


import com.dragonappear.inha.domain.selling.QSelling;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.item.ItemService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.dragonappear.inha.domain.selling.QSelling.*;

@RequiredArgsConstructor
@Repository
public class SellingRepositoryCustomImpl implements SellingRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Money findLowestSellingPrice(Long itemId) {
        Selling selling = jpaQueryFactory.selectFrom(QSelling.selling)
                .where(QSelling.selling.sellingStatus.eq(SellingStatus.판매입찰중).eq(QSelling.selling.auctionitem.item.id.eq(itemId)))
                .orderBy(QSelling.selling.auctionitem.price.amount.asc())
                .fetchOne();
        try{
            return selling.getAuctionitem().getPrice();
        }catch (Exception e){
            return Money.wons(0L);
        }
    }
}
