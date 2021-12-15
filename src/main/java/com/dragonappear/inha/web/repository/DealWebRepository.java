package com.dragonappear.inha.web.repository;


import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.fail.QFailInspection;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.user.QUser;
import com.dragonappear.inha.domain.value.CourierName;
import com.dragonappear.inha.web.repository.dto.QReturnDealWebDto;
import com.dragonappear.inha.web.repository.dto.QSendDealWebDto;
import com.dragonappear.inha.web.repository.dto.ReturnDealWebDto;
import com.dragonappear.inha.web.repository.dto.SendDealWebDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.auctionitem;
import static com.dragonappear.inha.domain.buying.QBuying.buying;
import static com.dragonappear.inha.domain.deal.QDeal.deal;
import static com.dragonappear.inha.domain.deal.value.DealStatus.검수탈락;
import static com.dragonappear.inha.domain.deal.value.DealStatus.검수합격;
import static com.dragonappear.inha.domain.inspection.fail.QFailInspection.*;
import static com.dragonappear.inha.domain.inspection.pass.QPassInspection.passInspection;
import static com.dragonappear.inha.domain.item.QItem.item;
import static com.dragonappear.inha.domain.payment.QPayment.payment;
import static com.dragonappear.inha.domain.selling.QSelling.selling;
import static com.dragonappear.inha.domain.user.QUser.*;

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
        return jpaQueryFactory.select(new QSendDealWebDto(deal.id
                        , buying.id
                        , payment.addressId
                        , buying.buyingStatus
                        , passInspection.id
                        , (passInspection.passDelivery==null) ? null : passInspection.passDelivery.delivery.courierName
                        , (passInspection.passDelivery==null) ? null : passInspection.passDelivery.delivery.invoiceNumber
                ))
                .from(deal)
                .join(deal.buying, buying)
                .join(buying.payment, payment)
                .join(passInspection).on(deal.inspection.id.eq(passInspection.id))
                .where(deal.dealStatus.eq(검수합격))
                .fetch();
    }

    public List<ReturnDealWebDto> getReturnList() {
        return jpaQueryFactory.select(new QReturnDealWebDto(deal.id
                        , selling.id
                        , selling.seller.userAddresses.get(0).id
                        , selling.sellingStatus
                        , passInspection.id
                        , (failInspection.failDelivery==null) ? null : failInspection.failDelivery.delivery.courierName
                        , (failInspection.failDelivery==null) ? null : failInspection.failDelivery.delivery.invoiceNumber
                ))
                .from(deal)
                .join(deal.selling, selling)
                .join(selling.seller, user)
                .join(failInspection).on(deal.inspection.id.eq(failInspection.id))
                .where(deal.dealStatus.eq(검수탈락))
                .fetch();
    }
}
