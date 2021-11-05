package com.dragonappear.inha.api.repository.user.dto;

import com.dragonappear.inha.domain.user.value.PointStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class MyPageUserPointDto {
    private PointStatus type;
    private LocalDateTime createdTime;
    private BigDecimal amount;

    @Builder
    @QueryProjection
    public MyPageUserPointDto(PointStatus status, LocalDateTime createdTime, BigDecimal amount) {
        this.type = status;
        this.createdTime = createdTime;
        this.amount = amount;
    }
}
