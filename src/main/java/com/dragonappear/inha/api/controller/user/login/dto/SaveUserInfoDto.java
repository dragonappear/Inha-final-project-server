package com.dragonappear.inha.api.controller.user.login.dto;

import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveUserInfoDto {
    private String email;
    private String username;
    private String userTel;
    private String nickname;
    private Address address;
    private Account account;

    @Builder
    public SaveUserInfoDto(String email, String username, String userTel, String nickname, Address address, Account account) {
        this.email = email;
        this.username = username;
        this.userTel = userTel;
        this.nickname = nickname;
        this.address = address;
        this.account = account;
    }
}
