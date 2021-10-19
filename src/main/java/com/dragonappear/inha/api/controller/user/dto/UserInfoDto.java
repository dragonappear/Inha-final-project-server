package com.dragonappear.inha.api.controller.user.dto;

import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoDto {
    private String username;
    private String email;
    private String userTel;
    private Address address;

    @Builder
    public UserInfoDto(String username, String email, String userTel, Address address) {
        this.username = username;
        this.email = email;
        this.userTel = userTel;
        this.address = address;
    }
}
