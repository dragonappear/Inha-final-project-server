package com.dragonappear.inha.api.controller.user.login.dto;

import com.dragonappear.inha.domain.user.value.UserRole;
import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginUserInfoDto {
    private Long userId;
    private String username;
    private String nickname;
    private String email;
    private String userTel;
    private Address address;
    private String userImageUrl;
    private UserRole userRole;

    @Builder
    public LoginUserInfoDto(Long userId, String username, String nickname, String email, String userTel, Address address, String userImageUrl, UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.userTel = userTel;
        this.address = address;
        this.userImageUrl = userImageUrl;
        this.userRole = userRole;
    }
}
