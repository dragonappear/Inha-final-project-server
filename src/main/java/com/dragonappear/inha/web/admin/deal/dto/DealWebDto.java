package com.dragonappear.inha.web.admin.deal.dto;

import com.dragonappear.inha.domain.deal.value.DealStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class DealWebDto {
    private Long dealId;
    private LocalDateTime createdDate;
    private DealStatus status;
    private Long buyingId;
    private Long sellingId;
    private Long inspectionId;

    @Builder
    public DealWebDto(Long dealId, LocalDateTime createdDate, DealStatus status, Long buyingId, Long sellingId, Long inspectionId) {
        this.dealId = dealId;
        this.createdDate = createdDate;
        this.status = status;
        this.buyingId = buyingId;
        this.sellingId = sellingId;
        this.inspectionId = inspectionId;
    }
}
