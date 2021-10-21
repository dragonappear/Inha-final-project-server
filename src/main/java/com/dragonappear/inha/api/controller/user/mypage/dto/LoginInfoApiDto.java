package com.dragonappear.inha.api.controller.user.mypage.dto;

import com.dragonappear.inha.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginInfoApiDto {
    private String email;
    private String userTel;


    public LoginInfoApiDto(User user) {
        this.email = user.getEmail();
        this.userTel = user.getUserTel();
    }
}
