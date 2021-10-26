package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.value.Money;
import org.springframework.stereotype.Repository;

import java.util.Map;

public interface BuyingRepositoryCustom {
    Map<Object,Object> findLargestBuyingPrice(Long itemId);
}
