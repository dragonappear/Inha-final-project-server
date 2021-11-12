package com.dragonappear.inha.domain.item.product;


import com.dragonappear.inha.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@DiscriminatorValue("monitor")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Monitor extends Item {
}
