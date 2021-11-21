package com.dragonappear.inha.web.admin.settlement.dto;

import com.dragonappear.inha.domain.value.BankName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositWebDto {
    private BigDecimal amount;
    private BankName bankName;
    private String accountHolder;
    private String accountNumber;
}
