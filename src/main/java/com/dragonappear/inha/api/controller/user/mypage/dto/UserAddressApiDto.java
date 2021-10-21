package com.dragonappear.inha.api.controller.user.mypage.dto;

import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UserAddressApiDto {
    private Long addressId;
    private Address address;

    @Builder
    public UserAddressApiDto(Long addressId, Address address) {
        this.addressId = addressId;
        this.address = address;
    }
}
