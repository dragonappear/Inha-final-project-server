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

@DiscriminatorValue("keyboard")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Keyboard extends Item {
    private String length;
    private String weight;
    private String keyType;
    private String type;

    @Builder
    public Keyboard(String itemName, String modelNumber, LocalDate releaseDay, String color, Money releasePrice, Category category
            , Manufacturer manufacturer, String length, String weight, String keyType, String type) {
        super(itemName, modelNumber, releaseDay, color, releasePrice, category, manufacturer);
        this.length = length;
        this.weight = weight;
        this.keyType = keyType;
        this.type = type;
    }
}
