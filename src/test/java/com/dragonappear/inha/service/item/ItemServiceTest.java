package com.dragonappear.inha.service.item;

import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.mysema.commons.lang.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ItemServiceTest {
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;

    @BeforeEach
    public void setUp() {
        Category category = new Category(CategoryName.노트북);
        Manufacturer manufacturer = new Manufacturer(ManufacturerName.삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Item("맥북", "modelNumber1", Money.wons(10000L),
                100L, Money.wons(20000L),category,manufacturer);
        itemRepository.save(item);
    }

    @Test
    public void 아이템_생성_테스트() throws Exception{
        //given
        Category category = categoryRepository.findAll().get(0);
        Manufacturer manufacturer = manufacturerRepository.findAll().get(0);
        Item item = new Item("맥북1", "modelNumber2", Money.wons(10000L),
                100L, Money.wons(20000L), category, manufacturer);
        //when
        Long save = itemService.save(item);
        Item find = itemRepository.findById(save).get();
        //then
        assertThat(find).isEqualTo(item);
        assertThat(find.getId()).isEqualTo(item.getId());
        assertThat(find.getItemName()).isEqualTo(item.getItemName());
        assertThat(find.getModelNumber()).isEqualTo(item.getModelNumber());
    }

    @Test
    public void 아이템_조회_테스트() throws Exception{
        //given
        Item newItem = itemRepository.findAll().get(0);
        //when
        Item findItem = itemService.findByItemId(newItem.getId());
        //then
        assertThat(findItem).isEqualTo(newItem);
        assertThat(findItem.getId()).isEqualTo(newItem.getId());
        assertThat(findItem.getItemName()).isEqualTo(newItem.getItemName());
        assertThat(findItem.getModelNumber()).isEqualTo(newItem.getModelNumber());
        assertThat(findItem.getReleasePrice()).isEqualTo(newItem.getReleasePrice());
    }

    @Test
    public void 아이템좋아요_카운트_테스트() throws Exception{
        //given
        Item find = itemRepository.findAll().get(0);
        //when
        Long like = itemService.likePlus(find);
        //then
        assertThat(find.getLikeCount()).isEqualTo(like);
        assertThat(find.getLikeCount()).isEqualTo(101L);
    }

    @Test
    public void 아이템좋아요취소_카운트_테스트() throws Exception{
        //given
        Item find = itemRepository.findAll().get(0);
        //when
        Long like = itemService.likeMinus(find);
        //then
        assertThat(find.getLikeCount()).isEqualTo(like);
        assertThat(find.getLikeCount()).isEqualTo(99L);
    }

    @Test
    public void 아이템출시가격_조회() throws Exception{
        //given
        Item findItem = itemRepository.findAll().get(0);
        //when
        Money amount = itemService.returnReleasePrice(findItem);
        //then
        assertThat(amount.getAmount()).isEqualTo(BigDecimal.valueOf(10_000L));
    }
    
    @Test
    public void 아이템조회_아이템이름으로_테스트() throws Exception{
        //given
        String itemName = "맥북";
        //when

        
        //then
    }
}