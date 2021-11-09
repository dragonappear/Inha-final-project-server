package com.dragonappear.inha.service.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.exception.NotFoundCustomException;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuctionItemService {
    private final AuctionitemRepository auctionitemRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long save(Item item, Money price) {
        return auctionitemRepository.save(new Auctionitem(item, price)).getId();
    }

    /**
     * READ
     */
    // 경매품아이디로 조회
    public Auctionitem findById(Long auctionitemId) {
      return auctionitemRepository.findById(auctionitemId).orElseThrow(() -> new NotFoundCustomException("존재하지 않는 경매품입니다."));

    }

    // 경매품아이디로 가격 조회
    public Money findPriceById(Long auctionitemId) {
        try {
            return auctionitemRepository.findById(auctionitemId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 경매품입니다.")).getPrice();
        } catch (Exception e) {
            return Money.wons(0L);
        }
    }


    /**
     * UPDATE
     */
    @Transactional
    public void updateItemLowestPrice(Item item, Money price) {
        if (item.getLowestPrice() == null || price.isLessThan(item.getLowestPrice())) {
            item.updateLowestPrice(price);
        }
    }
}
