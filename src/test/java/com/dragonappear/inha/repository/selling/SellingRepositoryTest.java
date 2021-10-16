package com.dragonappear.inha.repository.selling;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class SellingRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired SellingRepository sellingRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired CategoryRepository categoryRepository;

    @Test
    public void 판매생성_테스트() throws Exception{
        //given
        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        userRepository.save(newUser);
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        Item newItem = new Item("맥북", "serial1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                ,Money.wons(1_000_000L), Money.wons(1_000_000L), newCategory,newManufacturer);
        itemRepository.save(newItem);
        BidAuctionitem newBid = new BidAuctionitem(newItem,Money.wons(10_000_000_000L), of(now().getYear(), now().getMonth(), now().getDayOfMonth() + 1, now().getHour(), now().getMinute()));
        auctionitemRepository.save(newBid);
        Selling newSelling = new Selling(newUser, newBid);
        sellingRepository.save(newSelling);
        //when
        Selling findSelling = sellingRepository.findById(newSelling.getId()).get();
        //then
        assertThat(findSelling).isEqualTo(newSelling);
        assertThat(findSelling.getId()).isEqualTo(newSelling.getId());
        assertThat(findSelling.getSellingStatus()).isEqualTo(newSelling.getSellingStatus());
        assertThat(findSelling.getSeller()).isEqualTo(newSelling.getSeller());
        assertThat(findSelling.getAuctionitem()).isEqualTo(newSelling.getAuctionitem());
        assertThat(findSelling.getSellingDelivery()).isNull();
    }

}