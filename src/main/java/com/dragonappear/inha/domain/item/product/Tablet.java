package com.dragonappear.inha.domain.item.product;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.value.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@DiscriminatorValue("tablet")
@NoArgsConstructor(access = PROTECTED)
@Getter
//@Entity
public class Tablet extends Item {
    private String inch;
    private String cpu;
    private String core;
    private String os;
    private String memory;
    private String storage;
    private String gpu;
    private String weight;
    private String ppi;
    private String maxInjectionRate;

    @Builder
    public Tablet(String itemName, String modelNumber, LocalDate releaseDay, String color,
                  Money releasePrice, Category category, Manufacturer manufacturer, String inch,
                  String cpu, String core, String os, String memory, String storage, String gpu,
                  String weight, String ppi, String maxInjectionRate) {
        super(itemName, modelNumber, releaseDay, color, releasePrice, category, manufacturer);
        this.inch = inch;
        this.cpu = cpu;
        this.core = core;
        this.os = os;
        this.memory = memory;
        this.storage = storage;
        this.gpu = gpu;
        this.weight = weight;
        this.ppi = ppi;
        this.maxInjectionRate = maxInjectionRate;
    }
}
