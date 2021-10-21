package com.dragonappear.inha.api.controller.user.mypage.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class MyPageUserInfoDto {
    private String username;
    private String nickname;
    private String userRole;
    private String imageUrl;
    private BigDecimal userPoint;
    private int userLikeCount;

    @Builder
    public MyPageUserInfoDto(String username, String nickname,String imageUrl, String userRole, BigDecimal userPoint, int userLikeCount) {
        this.username = username;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.userRole = userRole;
        this.userPoint = userPoint;
        this.userLikeCount = userLikeCount;
    }
}
