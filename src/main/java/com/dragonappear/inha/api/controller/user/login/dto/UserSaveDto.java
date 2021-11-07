package com.dragonappear.inha.api.controller.user.login.dto;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserSaveDto {
    private String email;
    private String username;
    private String userTel;
    private String nickname;
    private Address address;
    private Account account;
    private String messageToken;

    @Builder
    public UserSaveDto(String email, String username, String userTel, String nickname, Address address, Account account, String messageToken) {
        this.email = email;
        this.username = username;
        this.userTel = userTel;
        this.nickname = nickname;
        this.address = address;
        this.account = account;
        this.messageToken = messageToken;
    }

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .userTel(this.userTel)
                .username(this.username)
                .nickname(this.nickname)
                .build();
    }
}
