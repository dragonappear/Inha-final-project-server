package com.dragonappear.inha.domain.item.product;

import com.dragonappear.inha.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@DiscriminatorValue("smartphone")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class SmartPhone extends Item {
}
