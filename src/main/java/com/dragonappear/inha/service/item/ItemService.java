package com.dragonappear.inha.service.item;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.exception.NotFoundCustomException;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final BuyingRepository buyingRepository;
    private final SellingRepository sellingRepository;


    /**
     * CREATE
     */

    // 아이템 등록
    @Transactional
    public Long save(Item item) {
        return itemRepository.save(item).getId();
    }

    //  아이템 조회 by 아이템 아이디
    public Item findByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundCustomException("존재하지않는 아이템입니다."));
    }

    /**
     * READ
     */

    // 모든 아이템 조회
    public List<Item> findAll() {
        List<Item> list = itemRepository.findAll();
        if (list.size() == 0) {
            throw new IllegalArgumentException("존재하지않는 아이템입니다.");
        }
        return list;
    }
    // 아이템 조회 by 아이템 이름
    public  List<Item> findByItemName(String itemName) {
        List<Item> list =  itemRepository.findByItemName(itemName);
        if (list.size() == 0) {
            throw new IllegalArgumentException("존재하지않는 아이템입니다.");
        }
        return list;
    }

    // 아이템 조회 by 모델명
    public Item findByModelNumber(String modelNumber) {
        return itemRepository.findByModelNumber(modelNumber)
                .orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다"));
    }

    // 아이템 조회 by 제조사 이름
    public List<Item> findByManufacturerName(ManufacturerName manufacturerName) {
        List<Item> list = itemRepository.findByManufacturerName(manufacturerName);
        if (list.size() == 0) {
            throw new IllegalArgumentException("아이템이 존재하지 않습니다");
        }
        return list;
    }

    // 아이템 조회 by 카테고리 이름
    public List<Item> findByCategoryName(CategoryName categoryName) {
        List<Item> list = itemRepository.findByCategoryName(categoryName);
        if (list.size() == 0) {
            throw new IllegalArgumentException("아이템이 존재하지 않습니다");
        }
        return list;
    }

    // 카테고리와 제조사명으로 아이템 조회
    public List<Item> findByCategoryAndManufacturer(CategoryName categoryName,ManufacturerName manufacturerName) {
        List<Item> list =  itemRepository.findByCategoryAndManufacturer(categoryName,manufacturerName);
        if (list.size() == 0) {
            throw new IllegalArgumentException("아이템이 존재하지 않습니다");
        }
        return list;
    }

    // 즉시 구매가 조회
    public Map<Object,Object> findInstantBuyingPrice(Long itemId) {
        Map<Object, Object> map = new HashMap<>();
        try {
            itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다"));
            return sellingRepository.findLowestSellingPrice(itemId);
        } catch (Exception e) {
            map.put("auctionitemId", "해당 아이템 판매입찰이 존재하지 않습니다");
            map.put("amount", Money.wons(0L).getAmount());
            return map;
        }
    }

    // 즉시 판매가 조회
    public Map<Object,Object> findInstantSellingPrice(Long itemId) {
        Map<Object, Object> map = new HashMap<>();
        try {
            itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다"));
            return buyingRepository.findLargestBuyingPrice(itemId);
        } catch (Exception e) {
            map.put("auctionitemId", "해당 아이템 구매입찰이 존재하지 않습니다");
            map.put("price", Money.wons(0L).getAmount());
            return map;
        }
    }

    /**
     * UPDATE
     */

    // 아이템 좋아요 카운트
    @Transactional
    public Long likePlus(Item item) {
       return itemRepository.findById(item.getId())
                .orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다"))
                .like();
    }

    // 아이템 좋아요 취소
    @Transactional
    public Long likeMinus(Item item) {
        return itemRepository.findById(item.getId())
                .orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다"))
                .cancel();
    }

    // 출시가격 조회
    public Money returnReleasePrice(Item item) {
        return itemRepository.findById(item.getId())
                .orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다"))
                .getReleasePrice();
    }

}
