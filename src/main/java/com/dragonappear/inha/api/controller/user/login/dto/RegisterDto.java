package com.dragonappear.inha.api.controller.user.login.dto;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String userTel;
    @NotNull
    private String nickname;
    @NotNull
    private String password;
    @NotNull
    private Address address;
    @NotNull
    private Account account;
    @NotNull
    private String messageToken;

    public User toEntity(Role role, String encodedPassword) {
        return User.builder()
                .email(this.email)
                .userTel(this.userTel)
                .username(this.username)
                .password(encodedPassword)
                .nickname(this.nickname)
                .userRoles(new HashSet<>(Arrays.asList(role)))
                .build();
    }
}
