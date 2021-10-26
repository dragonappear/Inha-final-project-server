package com.dragonappear.inha.repository.selling;

import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.value.Money;

import java.util.Map;


public interface SellingRepositoryCustom {
    Map<Object,Object> findLowestSellingPrice(Long itemId);
}
