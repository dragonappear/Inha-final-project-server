package com.dragonappear.inha.service.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.*;
import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.CategoryName.태블릿;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.애플;
import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class BidAuctionItemServiceTest {
    @Autowired BidAuctionItemService bidAuctionItemService;
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
        Item item = new Item("맥북1", "modelNumber1", Money.wons(10000L),
                Money.wons(20000L),category,manufacturer);
        itemRepository.save(item);
    }

    @Test
    public void 입찰판매경품_등록_테스트() throws Exception{
        //given
        Item item = itemRepository.findAll().get(0);
        LocalDateTime now = now();
        //when
        Long save = bidAuctionItemService.save(item, Money.wons(4_000_000L), now.plusHours(1));
        Auctionitem auctionitem = auctionitemRepository.findById(save).get();
        //then
        assertThat(auctionitem.getId()).isEqualTo(save);
        assertThat(auctionitem.getPrice()).isEqualTo(Money.wons(4_000_000L));
        assertThat(auctionitem.getEndDate()).isEqualTo(now.plusHours(1));
        assertThat(auctionitem.getAuctionitemStatus()).isEqualTo(경매중);
        assertThat(auctionitem.getItem()).isEqualTo(item);
    }

    @Test
    public void 입찰판매경품_등록오류_테스트() throws Exception{
        //given
        Item newItem = itemRepository.findAll().get(0);
        //when
        //then
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class, () -> {bidAuctionItemService.save(newItem, Money.wons(4_000_000L), now().minusDays(1));}
        );
    }
}