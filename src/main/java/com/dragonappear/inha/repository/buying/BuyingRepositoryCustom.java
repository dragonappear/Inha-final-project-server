package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.value.Money;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface BuyingRepositoryCustom {
    Map<Object,Object> findLargestBuyingPrice(Long itemId);

    List<BidBuying> findByStatus(BuyingStatus buyingStatus);

    Long endBidBuying();

    List<Buying> findOverdueAndNotCanceled();
}
