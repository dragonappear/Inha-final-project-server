package com.dragonappear.inha.service.item;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    //  아이템 등록
    public Long save(Item item) {
        return itemRepository.save(item).getId();
    }

    //  아이템 조회 by 아이템 아이디
    public Item findByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("아이템이 존재하지 않습니다"));
    }

    // 아이템 좋아요 카운트
    @Transactional
    public Long likePlus(Item item) {
       return itemRepository.findById(item.getId())
                .orElseThrow(() -> new IllegalStateException("아이템이 존재하지 않습니다"))
                .like();
    }

    // 아이템 좋아요 취소
    @Transactional
    public Long likeMinus(Item item) {
        return itemRepository.findById(item.getId())
                .orElseThrow(() -> new IllegalStateException("아이템이 존재하지 않습니다"))
                .cancel();
    }

    // 출시가격 조회
    public Money returnReleasePrice(Item item) {
        return itemRepository.findById(item.getId())
                .orElseThrow(() -> new IllegalStateException("아이템이 존재하지 않습니다"))
                .getReleasePrice();
    }

    // 시세 조회
    /*public Money returnMarketPrice(Item item) {
        itemRepository.findById(item.getId())
                .orElseThrow(() -> new IllegalStateException("아이템이 존재하지 않습니다"))
                .g
    }*/

    // 아이템 조회 by 아이템 이름
    public  List<Item> findByItemName(String itemName) {
        return itemRepository.findByItemName(itemName);
    }

    // 아이템 조회 by 모델명
    public Item findByModelNumber(String modelNumber) {
        return itemRepository.findByModelNumber(modelNumber)
                .orElseThrow(()->new IllegalStateException("아이템이 존재하지 않습니다"));
    }

    // 아이템 조회 by 제조사 이름
    public List<Item> findByManufacturerName(ManufacturerName manufacturerName) {
        return itemRepository.findByManufacturerName(manufacturerName);
    }

    // 아이템 조회 by 카테고리 이름
    public List<Item> findByCategoryName(CategoryName categoryName) {
        return itemRepository.findByCategoryName(categoryName);
    }
}
