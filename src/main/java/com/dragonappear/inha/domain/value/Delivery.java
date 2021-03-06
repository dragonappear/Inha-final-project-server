package com.dragonappear.inha.domain.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.Objects;

import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@ToString
@NoArgsConstructor(access = PROTECTED)
@Embeddable
@Getter
public class Delivery {

    @Column(nullable = false)
    @Enumerated(STRING)
    private CourierName courierName;
    @Column(nullable = false)
    private String invoiceNumber;

    public Delivery(CourierName courierName, String invoiceNumber) {
        this.courierName = courierName;
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return getCourierName() == delivery.getCourierName() && Objects.equals(getInvoiceNumber(), delivery.getInvoiceNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourierName(), getInvoiceNumber());
    }
}
