package com.dragonappear.inha.api.controller.user.mypage.dto;

import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UserAddressApiDto {
    private List<Address> addresses;

    @Builder
    public UserAddressApiDto(List<Address> addresses) {
        this.addresses = addresses;
    }
}
