package com.dragonappear.inha.api.controller.user.dto;

import com.dragonappear.inha.domain.user.value.UserRole;
import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;

@Data
public class SaveUserInfoDto {
    private String email;
    private String username;
    private String userTel;
    private String nickname;
    private Address address;

    @Builder
    public SaveUserInfoDto(String email, String username, String userTel, String nickname, Address address) {
        this.email = email;
        this.username = username;
        this.userTel = userTel;
        this.nickname = nickname;
        this.address = address;
    }
}
