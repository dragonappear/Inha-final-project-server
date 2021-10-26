package com.dragonappear.inha.repository.selling;

import com.dragonappear.inha.domain.value.Money;



public interface SellingRepositoryCustom {
    Money findLowestSellingPrice(Long itemId);
}
