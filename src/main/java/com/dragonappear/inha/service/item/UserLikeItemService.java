package com.dragonappear.inha.service.item;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.UserLikeItem;
import com.dragonappear.inha.repository.item.UserLikeItemRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserLikeItemService {
    private final UserLikeItemRepository userLikeItemRepository;

    /**
     * CREATE
     */

    // 회원아이템 찜 저장
    @Transactional
    public Long save(UserLikeItem userLikeItem)  {
        return userLikeItemRepository.save(userLikeItem).getId();
    }

    /**
     * READ
     */

    // 아이템 찜 조회 by 유저아이디
    public List<Item> findByUserId(Long userId) {
        List<UserLikeItem> all = userLikeItemRepository.findByUserId(userId);
        List<Item> items = new ArrayList<>();
        all.stream().forEach(userLikeItem -> items.add(userLikeItem.getItem()));
        return items;
    }
    // 유저가 누른 아이템 좋아요 개수 조회
    public int getLikeNumber(Long userId) {
        return userLikeItemRepository.findByUserId(userId).size();
    }

    // 좋아요아이템 조회 by 유저아이디와 아이템아이디
    public UserLikeItem findByUserIdAndItemId(Long userId, Long itemId) {
        return userLikeItemRepository.findByUserIdAndItemId(userId, itemId).orElse(null);
    }

    /**
     * DELETE
     */

    // 회원아이템 찜 취소
    @Transactional
    public void delete(UserLikeItem userLikeItem) {
        userLikeItem.getItem().cancel();
        userLikeItemRepository.delete(userLikeItem.getId());
    }
}
