package com.dragonappear.inha.repository.selling;

import com.dragonappear.inha.domain.value.Money;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingRepositoryCustom {

    Money findLowestSellingPrice(Long itemId);
}
