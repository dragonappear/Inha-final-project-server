package com.dragonappear.inha.web.admin.inspection.dto;

import com.dragonappear.inha.domain.deal.value.DealStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InspectionWebDto {
    private Long dealId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private DealStatus status;
    private Long inspectionId;
}
