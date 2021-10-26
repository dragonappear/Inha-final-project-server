package com.dragonappear.inha.api.returndto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class DetailDto<T> {
    private List<String> fileNames;
    private T detail;

    @Builder
    public DetailDto(List<String> fileNames, T detail) {
        this.fileNames = fileNames;
        this.detail = detail;
    }
}