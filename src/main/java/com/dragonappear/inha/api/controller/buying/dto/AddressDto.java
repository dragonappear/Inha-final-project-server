package com.dragonappear.inha.api.controller.buying.dto;

import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddressDto {
    private Long addressId;
    private Address address;

    @Builder
    public AddressDto(Long addressId,Address address) {
        this.addressId = addressId;
        this.address = address;
    }
}