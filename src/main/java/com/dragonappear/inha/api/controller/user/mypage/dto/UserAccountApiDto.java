package com.dragonappear.inha.api.controller.user.mypage.dto;

import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.value.Account;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserAccountApiDto {
    private Long id;
    private Account account;

    @Builder
    public UserAccountApiDto(UserAccount account) {
        this.id = account.getId();
        this.account = account.getUserAccount();
    }
}
