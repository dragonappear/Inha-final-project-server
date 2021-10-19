package com.dragonappear.inha.api.controller.user.dto;

import com.dragonappear.inha.domain.user.UserAddress;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class SessionUserDto {
    private String username;
    private String email;
    private Boolean isNullUserTel;
    private Boolean isNullUserAddress;

    @Builder
    public SessionUserDto(String username, String email, String userTel, List<UserAddress> userAddresses) {
        this.username = username;
        this.email = email;
        this.isNullUserTel = (userTel == null) ? true : false;
        this.isNullUserAddress = (userAddresses.size()==0) ? true : false;
    }
}
