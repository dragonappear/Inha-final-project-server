package com.dragonappear.inha.api.controller.user.deal.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class PointDto {
    private BigDecimal total;
    private String inspectionFee;
    private String deliveryFee;

    @Builder
    public PointDto(BigDecimal total, String inspectionFee, String deliveryFee) {
        this.total = total;
        this.inspectionFee = inspectionFee;
        this.deliveryFee = deliveryFee;
    }
}