package com.dragonappear.inha.api.controller.user.mypage.read.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PointDto<T> {
    private int count;
    private BigDecimal total;
    private List<T> items;
    @Builder
    public PointDto(int count, BigDecimal total, List<T> items) {
        this.count = count;
        this.total = total;
        this.items = items;
    }
    public static <T> PointDto getResults(BigDecimal total, List<T> dtos) {
        return PointDto.builder()
                .count(dtos.size())
                .total(total)
                .items(dtos.stream().map(dto -> (Object) dto).collect(Collectors.toList()))
                .build();
    }
}