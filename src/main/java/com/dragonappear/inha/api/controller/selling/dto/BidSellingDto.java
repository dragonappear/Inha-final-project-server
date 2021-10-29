package com.dragonappear.inha.api.controller.selling.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BidSellingDto extends SellingDto{
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    @Builder
    public BidSellingDto(Long userId, Long itemId, BigDecimal price, LocalDateTime endDate) {
        super(userId, itemId, price);
        this.endDate = endDate;
    }
}
