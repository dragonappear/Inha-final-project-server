package com.dragonappear.inha.service.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuctionItemService {
    @Autowired AuctionitemRepository auctionitemRepository;

    // 경매아이템 등록
    public Long save(Auctionitem auctionitem) {
        return auctionitemRepository.save(auctionitem).getId();
    }
}
