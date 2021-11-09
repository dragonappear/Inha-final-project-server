package com.dragonappear.inha.api.repository.deal;

import com.dragonappear.inha.api.repository.deal.dto.MarketPriceInfoDto;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.deal.QDeal.*;
import static com.dragonappear.inha.domain.selling.QSelling.*;
import static com.dragonappear.inha.domain.selling.value.SellingStatus.*;

@RequiredArgsConstructor
@Repository
public class DealQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<MarketPriceInfoDto> findRecentPrice(Long itemId, Pageable pageable) {
        List<Tuple> result = queryFactory.select(selling.auctionitem.price, deal.createdDate)
                .from(deal)
                .leftJoin(deal.selling, selling)
                .where(selling.auctionitem.item.id.eq(itemId).and(selling.sellingStatus.eq(거래중)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return result.stream().map(tuple -> {
            return MarketPriceInfoDto.builder()
                    .amount(tuple.get(selling.auctionitem.price).getAmount())
                    .localDate(tuple.get(deal.createdDate).toLocalDate())
                    .build();
        }).collect(Collectors.toList());
    }


}
