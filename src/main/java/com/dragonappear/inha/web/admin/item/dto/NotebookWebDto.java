package com.dragonappear.inha.web.admin.item.dto;

import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.value.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotebookWebDto {
    private String itemName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDay;
    private String color;
    private String modelNumber;
    private Long releasePrice;
    private String manufacturerName;
    private String inch;
    private String cpu;
    private String core;
    private String os;
    private String memory;
    private String storage;
    private String gpu;
    private String weight;
}
