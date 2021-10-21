package com.dragonappear.inha.api.controller.user.mypage.dto;

import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.BankName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserAccountDto {
    private Account account;
}
