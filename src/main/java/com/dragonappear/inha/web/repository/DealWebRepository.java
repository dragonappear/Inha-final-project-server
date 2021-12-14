package com.dragonappear.inha.web.repository;


import com.dragonappear.inha.domain.buying.QBuying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.inspection.QInspection;
import com.dragonappear.inha.domain.payment.QPayment;
import com.dragonappear.inha.web.repository.dto.ReturnDealWebDto;
import com.dragonappear.inha.web.repository.dto.SendDealWebDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.auctionitem;
import static com.dragonappear.inha.domain.buying.QBuying.*;
import static com.dragonappear.inha.domain.deal.QDeal.deal;
import static com.dragonappear.inha.domain.deal.value.DealStatus.*;
import static com.dragonappear.inha.domain.inspection.QInspection.*;
import static com.dragonappear.inha.domain.item.QItem.item;
import static com.dragonappear.inha.domain.payment.QPayment.*;
import static com.dragonappear.inha.domain.selling.QSelling.selling;

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

    public List<SendDealWebDto> getSendList() {
        /*jpaQueryFactory.select(new SendDealWebDto(deal.id
                ,buying.id
                ,payment.addressId
                ,buying.buyingStatus
                ,inspection.
                ))
                .from(deal)
                .join(deal.buying, buying)
                .join(buying.payment, payment)
                .join(deal.inspection, inspection)
                .where(deal.dealStatus.eq(검수합격))
                .fetch();*/
        return null;




    }

    public List<ReturnDealWebDto> getReturnList() {

        return null;
    }
}
