package com.dragonappear.inha.domain.value;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Address {

    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private String contactTel;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String detail;
    @Column(nullable = false)
    private String zipcode;

    @Builder
    public Address(String recipient, String contactTel, String city, String street, String detail, String zipcode) {
        this.recipient = recipient;
        this.contactTel = contactTel;
        this.city = city;
        this.street = street;
        this.detail = detail;
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getDetail(), address.getDetail()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getDetail(), getZipcode());
    }


    /**
     * 비즈니스 로직
     */

    public void update(Address address) {
        this.recipient = address.getRecipient();
        this.contactTel = address.getContactTel();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.detail = address.getDetail();
        this.zipcode = address.getZipcode();
    }

}
