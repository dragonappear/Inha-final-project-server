package com.dragonappear.inha.web.admin.deal.dto;

import com.dragonappear.inha.domain.deal.value.DealStatus;
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
public class DealWebDto {
    private Long dealId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private DealStatus status;
    private Long itemId;
    private BigDecimal amount;
    private Long buyingId;
    private Long sellingId;
    private Long inspectionId;
}
