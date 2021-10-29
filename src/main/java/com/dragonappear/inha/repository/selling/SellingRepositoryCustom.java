package com.dragonappear.inha.repository.selling;

import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.selling.SellingService;

import java.util.List;
import java.util.Map;


public interface SellingRepositoryCustom {
    Map<Object,Object> findLowestSellingPrice(Long itemId);

    Long endBidSelling();
}
