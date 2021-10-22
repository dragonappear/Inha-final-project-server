package com.dragonappear.inha.api.controller.user.mypage.dto;

import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.value.Account;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserAccountApiDto {
    private Long userAccountId;
    private Account account;

    @Builder
    public UserAccountApiDto(UserAccount account) {
        this.userAccountId = account.getId();
        this.account = account.getUserAccount();
    }
}
