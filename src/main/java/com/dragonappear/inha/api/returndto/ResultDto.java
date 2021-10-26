package com.dragonappear.inha.api.returndto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class ResultDto<T> {
    private int count;
    private List<T> content;
    @Builder
    public ResultDto(int count, List<T> content) {
        this.count = count;
        this.content = content;
    }

    public static <T> ResultDto returnResults(List<T> dtos) {
        return ResultDto.builder()
                .count(dtos.size())
                .content(dtos.stream().map(dto -> (Object) dto).collect(Collectors.toList()))
                .build();
    }
}