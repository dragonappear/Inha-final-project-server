package com.dragonappear.inha.service.selling;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import com.dragonappear.inha.service.auctionitem.BidAuctionItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static com.dragonappear.inha.domain.selling.value.SellingStatus.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class SellingServiceTest {
    @Autowired SellingRepository sellingRepository;
    @Autowired SellingService sellingService;
    @Autowired BidAuctionItemService bidAuctionItemService;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User newUser1 = new User("name1", "nickname1", "email1@", "userTel11");
        userRepository.save(newUser1);

        User newUser2 = new User("name1", "nickname1", "email2@", "userTel11");
        userRepository.save(newUser2);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Item("맥북1", "modelNumber1", Money.wons(10000L),
                Money.wons(20000L),category,manufacturer);
        itemRepository.save(item);

        BidAuctionitem bidAuctionitem = new BidAuctionitem(item, Money.wons(4_000_000L), LocalDateTime.now().plusHours(1));
        auctionitemRepository.save(bidAuctionitem);

        BidAuctionitem bidAuctionitem1 = new BidAuctionitem(item, Money.wons(5_000_000L), LocalDateTime.now().plusHours(1));
        auctionitemRepository.save(bidAuctionitem1);

        Selling selling = new Selling(newUser1, bidAuctionitem);
        sellingRepository.save(selling);
    }

    @Test
    public void 판매_등록_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(1);
        //when
        Long save = sellingService.save(user, auctionitem);
        Selling selling = sellingRepository.findById(save).get();
        //then
        assertThat(selling.getId()).isEqualTo(save);
        assertThat(selling.getSellingStatus()).isEqualTo(판매중);
        assertThat(selling.getAuctionitem()).isEqualTo(auctionitem);
        assertThat(selling.getSeller()).isEqualTo(user);
        assertThat(selling.getSellingDelivery()).isNull();
    }

    @Test
    public void 판매내역조회_유저아이디로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(0);
        Auctionitem auctionitem1 = auctionitemRepository.findAll().get(1);
        Selling selling = new Selling(user, auctionitem);
        Selling selling1 = new Selling(user, auctionitem1);
        sellingRepository.save(selling);
        sellingRepository.save(selling1);
        //when
        List<Selling> all = sellingService.findByUserId(user.getId());
        //then
        assertThat(all.size()).isEqualTo(3);
        org.assertj.core.api.Assertions.assertThat(all).extracting("auctionitem").containsOnly(auctionitem, auctionitem1);
        org.assertj.core.api.Assertions.assertThat(all).extracting("seller").containsOnly(user);
    }

    @Test
    public void 판매내역조회_상품이름으로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(0);
        Auctionitem auctionitem1 = auctionitemRepository.findAll().get(1);
        Selling selling = new Selling(user, auctionitem);
        Selling selling1 = new Selling(user, auctionitem1);
        sellingRepository.save(selling);
        sellingRepository.save(selling1);
        //when
        List<Selling> all = sellingService.findByItemName("맥북1");
        //then
        Assertions.assertThat(all.size()).isEqualTo(3);
        Assertions.assertThat(all).extracting("auctionitem").containsOnly(auctionitem, auctionitem1);
        Assertions.assertThat(all).extracting("seller").containsOnly(user);
    }
    
    @Test
    public void 판매_중복등록_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(0);
        auctionitem.updateStatus(AuctionitemStatus.거래중);
        //when
        //then
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class, () ->
                {Long save = sellingService.save(user, auctionitem);}
                );
    }

}