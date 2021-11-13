package com.dragonappear.inha.service.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class AuctionItemServiceTest {
    @Autowired AuctionItemService auctionItemService;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);
        Item item = new Notebook("맥북1", "modelNumber1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                , Money.wons(10000L),
                category,manufacturer);
        itemRepository.save(item);
    }

    @Test
    public void 입찰판매경품_등록_테스트() throws Exception{
        //given
        Item item = itemRepository.findAll().get(0);
        LocalDateTime now = now();
        //when
        Long save = auctionItemService.save(item, Money.wons(4_000_000L));
        Auctionitem auctionitem = auctionitemRepository.findById(save).get();
        //then
        assertThat(auctionitem.getId()).isEqualTo(save);
        assertThat(auctionitem.getPrice()).isEqualTo(Money.wons(4_000_000L));
        assertThat(auctionitem.getItem()).isEqualTo(item);
    }

}