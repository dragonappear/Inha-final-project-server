package com.dragonappear.inha.api.controller.user.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyPageUserInfoDto {
    private String username;
    private String nickname;
    private String userRole;
    private String imageUrl;
    private BigDecimal userPoint;
    private int userLikeCount;
}
