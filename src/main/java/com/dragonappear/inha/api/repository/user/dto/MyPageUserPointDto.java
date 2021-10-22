package com.dragonappear.inha.api.repository.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class MyPageUserPointDto {
    private String type;
    private LocalDateTime createdTime;
    private BigDecimal amount;

    @Builder
    @QueryProjection
    public MyPageUserPointDto(String type, LocalDateTime createdTime, BigDecimal amount) {
        this.type = type;
        this.createdTime = createdTime;
        this.amount = amount;
    }
}
