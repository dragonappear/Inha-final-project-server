package com.dragonappear.inha.api.repository.auctionitem;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class AuctionitemDto {
    private Long id;
    private BigDecimal amount;

    @Builder
    @QueryProjection
    public AuctionitemDto(Long id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }
}
