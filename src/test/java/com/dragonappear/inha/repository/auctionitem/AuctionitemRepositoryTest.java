package com.dragonappear.inha.repository.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
                , Money.wons(1_000_000L),  Money.wons(1_000_000L), newCategory,newManufacturer);
        itemRepository.save(newItem);
        BidAuctionitem newBid = new BidAuctionitem(newItem,Money.wons(10_000_000_000L), of(now().getYear(), now().getMonth(), now().getDayOfMonth() + 1, now().getHour(), now().getMinute()));
        auctionitemRepository.save(newBid);
        //when
        BidAuctionitem findBid = (BidAuctionitem)auctionitemRepository.findById(newBid.getId()).get();
        //then
        assertThat(findBid).isEqualTo(newBid);
        assertThat(findBid.getId()).isEqualTo(newBid.getId());
        assertThat(findBid.getPrice()).isEqualTo(newBid.getPrice());
        assertThat(findBid.getStartDate()).isEqualTo(newBid.getStartDate());
        assertThat(findBid.getEndDate()).isEqualTo(newBid.getEndDate());
        assertThat(findBid.getAuctionitemStatus()).isEqualTo(newBid.getAuctionitemStatus());
    }
}