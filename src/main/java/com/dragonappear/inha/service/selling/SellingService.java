package com.dragonappear.inha.service.selling;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.repository.selling.SellingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SellingService {
    @Autowired SellingRepository sellingRepository;

    @Transactional
    // 판매 등록
    public Long save(User user,Auctionitem auctionitem) {
        return sellingRepository.save(new Selling(user, auctionitem)).getId();
    }
}
