package com.dragonappear.inha.api.controller.selling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellingDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long itemId;
    @NotNull
    private BigDecimal price;

}
