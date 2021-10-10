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
import com.dragonappear.inha.service.user.UserService;
import com.mysema.commons.lang.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.dragonappear.inha.domain.item.value.CategoryName.*;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.*;
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
        Category category = new Category(노트북);
        Category category1 = new Category(태블릿);
        Manufacturer manufacturer = new Manufacturer(삼성);
        Manufacturer manufacturer1 = new Manufacturer(애플);
        categoryRepository.save(category);
        categoryRepository.save(category1);
        manufacturerRepository.save(manufacturer);
        manufacturerRepository.save(manufacturer1);

        Item item = new Item("맥북1", "modelNumber1", Money.wons(10000L),
                 Money.wons(20000L),category,manufacturer);
        Item item1 = new Item("맥북1", "modelNumber2", Money.wons(20000L),
                 Money.wons(20000L),category,manufacturer);
        Item item2 = new Item("맥북2", "modelNumber3", Money.wons(30000L),
                 Money.wons(20000L),category1,manufacturer1);
        Item item3 = new Item("맥북2", "modelNumber4", Money.wons(40000L),
                 Money.wons(20000L),category1,manufacturer1);

        itemRepository.save(item);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
    }

    @Test
    public void 아이템_생성_테스트() throws Exception{
        //given
        Category category = categoryRepository.findAll().get(0);
        Manufacturer manufacturer = manufacturerRepository.findAll().get(0);
        Item item = new Item("맥북1", "modelNumber10", Money.wons(10000L),
                 Money.wons(20000L), category, manufacturer);
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
        assertThat(find.getLikeCount()).isEqualTo(1L);
    }

    @Test
    public void 아이템좋아요취소_카운트_테스트() throws Exception{
        //given
        Item find = itemRepository.findAll().get(0);
        //when
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class, () -> {
                    itemService.likeMinus(find);
                }
        );
        //then
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
        String itemName = "맥북1";
        //when
        List<Item> all = itemService.findByItemName(itemName);
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).extracting("releasePrice").containsOnly(Money.wons(10000L), Money.wons(20000L));
        assertThat(all).extracting("latestPrice").containsOnly(Money.wons(20000L));
    }

    @Test
    public void 아이템조회_모델명으로_테스트() throws Exception{
        //given
        String modelNumber = "modelNumber2";
        //when
        Item findItem = itemService.findByModelNumber(modelNumber);
        //then
        assertThat(findItem.getModelNumber()).isEqualTo("modelNumber2");
    }

    @Test
    public void 아이템조회_제조사이름으로_테스트() throws Exception{
        //given
        ManufacturerName name = 삼성;
        //when
        List<Item> all = itemService.findByManufacturerName(name);
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).extracting("modelNumber").containsOnly("modelNumber1", "modelNumber2");
    }

    @Test
    public void 아이템조회_카테고리이름으로_테스트() throws Exception{
        //given
        CategoryName categoryName = 태블릿;
        //when
        List<Item> all = itemService.findByCategoryName(categoryName);
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).extracting("modelNumber").containsOnly("modelNumber3", "modelNumber4");
    }
}