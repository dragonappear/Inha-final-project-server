package com.dragonappear.inha.domain.item.product;

import com.dragonappear.inha.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;


@DiscriminatorValue("tablet")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Tablet extends Item {

}
