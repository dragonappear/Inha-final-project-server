package com.dragonappear.inha.api.controller.deal.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
//@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class DealApiDto {
    private String itemName;
    private String itemImageFileName;
    private BigDecimal price;
    private LocalDateTime createDate;
    private Long buyingId;
    private Long sellingId;

    @Builder
    public DealApiDto(String itemName, String itemImageFileName, BigDecimal price, LocalDateTime createDate, Long buyingId, Long sellingId) {
        this.itemName = itemName;
        this.itemImageFileName = itemImageFileName;
        this.price = price;
        this.createDate = createDate;
        this.buyingId = buyingId;
        this.sellingId = sellingId;
    }
}
