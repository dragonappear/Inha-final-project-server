package com.dragonappear.inha.api.repository.seliing;

import com.dragonappear.inha.api.repository.seliing.dto.*;
import com.dragonappear.inha.domain.selling.Selling;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.auctionitem.QAuctionitem.auctionitem;
import static com.dragonappear.inha.domain.deal.QDeal.deal;
import static com.dragonappear.inha.domain.item.QItemImage.itemImage1;
import static com.dragonappear.inha.domain.selling.QBidSelling.*;
import static com.dragonappear.inha.domain.selling.QSelling.selling;
import static com.dragonappear.inha.domain.selling.value.SellingStatus.*;

@RequiredArgsConstructor
@Repository
public class SellingQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MyPageUserSellingSimpleDto getMyPageUserSellingSimpleDto(Long userId) {
        List<Selling> results = jpaQueryFactory.selectFrom(selling)
                .where(selling.seller.id.eq(userId))
                .fetch();
        MyPageUserSellingSimpleDto dto = new MyPageUserSellingSimpleDto();
        results.stream().forEach(result ->{
            if(result.getSellingStatus()== 판매입찰중){
                dto.countBidding();
            }
            else if(result.getSellingStatus()== 거래진행 ||
                    result.getSellingStatus()== 입고완료 ||
                    result.getSellingStatus()== 검수진행 ||
                    result.getSellingStatus()== 검수합격 ||
                    result.getSellingStatus()== 검수탈락){
                dto.countOngoing();
            } else{
                dto.countEnd();
            }
            dto.countTotal();
        });
        return dto;
    }


    public List<MyPageUserSellingBidDto> getMyPageUserSellingBidDto(Long userId) {
        return jpaQueryFactory.select(
                        new QMyPageUserSellingBidDto(itemImage1.itemImage.fileOriName
                                , auctionitem.item.itemName
                                , auctionitem.price.amount
                                , bidSelling.endDate)
                )
                .from(bidSelling)
                .join(bidSelling.auctionitem, auctionitem).on(auctionitem.id.eq(bidSelling.auctionitem.id))
                .join(itemImage1).on(itemImage1.item.id.eq(bidSelling.auctionitem.item.id))
                .where(bidSelling.sellingStatus.in(판매입찰중).and(bidSelling.seller.id.eq(userId)))
                .fetch();
    }

    //
    public List<MyPageUserSellingOngoingDto> getMyPageUserSellingOngoingDto(Long userId) {
        return jpaQueryFactory.select(
                        new QMyPageUserSellingOngoingDto(selling.id
                                , itemImage1.itemImage.fileOriName
                                , selling.auctionitem.item.itemName
                                , deal.dealStatus)
                )
                .from(selling)
                .join(deal).on(deal.selling.id.eq(selling.id))
                .join(itemImage1).on(itemImage1.item.id.eq(selling.auctionitem.item.id))
                .join(selling.auctionitem,auctionitem).on(auctionitem.id.eq(selling.auctionitem.id))
                .where(selling.sellingStatus.in(거래진행,입고완료,검수진행,검수합격,검수탈락).and(selling.seller.id.eq(userId)))
                .fetch();
    }

    public List<MyPageUserSellingEndDto> getMyPageUserSellingEndDto(Long userId) {
        return jpaQueryFactory.select(new QMyPageUserSellingEndDto(selling.id
                                , itemImage1.itemImage.fileOriName
                                , auctionitem.item.itemName
                                , selling.createdDate
                                , selling.sellingStatus))
                .from(selling)
                .join(selling.auctionitem,auctionitem).on(auctionitem.id.eq(selling.auctionitem.id))
                .join(itemImage1).on(itemImage1.item.id.eq(selling.auctionitem.item.id))
                .where(selling.sellingStatus.in(판매입찰종료, 정산완료, 미입고취소, 검수탈락취소,반송완료).and(selling.seller.id.eq(userId)))
                .fetch();
    }
}
