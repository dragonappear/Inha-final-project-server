package com.dragonappear.inha.api.controller.user.login.dto;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
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

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .userTel(this.userTel)
                .username(this.username)
                .nickname(this.nickname)
                .build();
    }
}
