package com.dragonappear.inha.api.controller.selling.dto;

import com.dragonappear.inha.domain.value.Delivery;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SellingDeliveryDto {
    private Long sellingId;
    private Delivery delivery;

    @Builder
    public SellingDeliveryDto(Long sellingId, Delivery delivery) {
        this.sellingId = sellingId;
        this.delivery = delivery;
    }
}
