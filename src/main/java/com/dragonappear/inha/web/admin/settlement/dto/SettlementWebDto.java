package com.dragonappear.inha.web.admin.settlement.dto;

import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SettlementWebDto {
    private Long dealId;
    private Long inspectionId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private BigDecimal settlementAmount;
    private BigDecimal saleAmount;
    private DealStatus dealStatus;
    private SellingStatus sellingStatus;
    private Long sellerId;
    private String bankName;
    private String accountNumber;
    private String accountHolder;
}
