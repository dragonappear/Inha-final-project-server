package com.dragonappear.inha.repository.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback
class AuctionitemRepositoryTest {
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired CategoryRepository categoryRepository;

    @Test
    public void 경매상품생성_테스트() throws Exception{
        //given
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        //given
        Item newItem = new Item("맥북", "serial1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                , Money.wons(1_000_000L),   newCategory,newManufacturer);
        itemRepository.save(newItem);
        Auctionitem auctionitem = new Auctionitem(newItem,Money.wons(10_000_000_000L));
        auctionitemRepository.save(auctionitem);
        //when
        Auctionitem findItem = auctionitemRepository.findById(auctionitem.getId()).get();
        //then
        assertThat(findItem).isEqualTo(auctionitem);
        assertThat(findItem.getId()).isEqualTo(auctionitem.getId());
        assertThat(findItem.getPrice()).isEqualTo(auctionitem.getPrice());
    }
}