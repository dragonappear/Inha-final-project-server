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

@DiscriminatorValue("monitor")
@NoArgsConstructor(access = PROTECTED)
@Getter
//@Entity
public class Monitor extends Item {
    private String inch;
    private String displayRate;
    private String panelType;
    private String resolution;
    private Boolean dpPort;
    private Boolean hdmi;
    private String maxInjectionRate;

    @Builder
    public Monitor(String itemName, String modelNumber, LocalDate releaseDay
            , String color, Money releasePrice, Category category, Manufacturer manufacturer
            , String inch, String displayRate, String panelType, String resolution
            , Boolean dpPort, Boolean hdmi, String maxInjectionRate) {
        super(itemName, modelNumber, releaseDay, color, releasePrice, category, manufacturer);
        this.inch = inch;
        this.displayRate = displayRate;
        this.panelType = panelType;
        this.resolution = resolution;
        this.dpPort = dpPort;
        this.hdmi = hdmi;
        this.maxInjectionRate = maxInjectionRate;
    }
}
