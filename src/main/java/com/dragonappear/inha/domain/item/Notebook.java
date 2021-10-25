package com.dragonappear.inha.domain.item;

import com.dragonappear.inha.domain.value.Money;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@DiscriminatorValue("notebook")
@NoArgsConstructor(access = PROTECTED)
@Data
@Entity
public class Notebook extends Item{
    private String inch;
    private String cpu;
    private String core;
    private String os;
    private String memory;
    private String storage;
    private String gpu;
    private String weight;

    @Builder
    public Notebook(String itemName, String modelNumber, LocalDate releaseDay, String color
            , Money releasePrice,  Category category, Manufacturer manufacturer
            , String inch, String cpu, String core, String os, String memory, String storage, String gpu, String weight) {
        super(itemName, modelNumber, releaseDay, color, releasePrice, category, manufacturer);
        this.inch = inch;
        this.cpu = cpu;
        this.core = core;
        this.os = os;
        this.memory = memory;
        this.storage = storage;
        this.gpu = gpu;
        this.weight = weight;
    }
}
