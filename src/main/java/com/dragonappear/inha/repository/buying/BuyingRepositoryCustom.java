package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.value.Money;
import org.springframework.stereotype.Repository;

public interface BuyingRepositoryCustom {
    Money findLargestBuyingPrice(Long itemId);
}
