package com.dragonappear.inha.api.controller.user.login.dto;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
    private String email;
    private String username;
    private String userTel;
    private String nickname;
    private Address address;
    private Account account;

    @Builder
    public UserDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.userTel = user.getUserTel();
        this.nickname = user.getNickname();
        this.address = (user.getUserAddresses().size()==0) ? null : user.getUserAddresses().get(0).getUserAddress();
        this.account = (user.getUserAccount()==null) ? null : user.getUserAccount().getUserAccount();
    }
}
