package com.dragonappear.inha.api.controller.selling.dto;

import com.dragonappear.inha.domain.value.Delivery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellingDeliveryDto {
    @NotNull
    private Long sellingId;
    @NotNull
    private Delivery delivery;

}
