package com.dragonappear.inha.api.controller.inspection.dto;

import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class InspectionApiDto {
    private List<String> fileNames = new ArrayList<>();
    private String result;

    @Builder
    public InspectionApiDto(Inspection inspection, List<String> fileNames) {
        if (inspection instanceof PassInspection) {
            this.result = "검수합격";
        } else{
            this.result = "검수탈락";
        }
        this.fileNames = fileNames;
    }
}
