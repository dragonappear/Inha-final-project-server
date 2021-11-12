package com.dragonappear.inha.service.selling;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.selling.InstantSelling;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static com.dragonappear.inha.domain.selling.value.SellingStatus.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback
class SellingServiceTest {
    @Autowired SellingRepository sellingRepository;
    @Autowired SellingService sellingService;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User newUser1 = new User("name1", "nickname1", "email1@", "userTel11231");
        userRepository.save(newUser1);

        User newUser2 = new User("name1", "nickname1", "email2@", "userTel11231231");
        userRepository.save(newUser2);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Notebook("맥북1", "modelNumber1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                , Money.wons(10000L),
               category,manufacturer);
        itemRepository.save(item);

        Auctionitem auctionitem = new Auctionitem(item, Money.wons(4_000_000L));
        auctionitemRepository.save(auctionitem);

        Auctionitem auctionitem1 = new Auctionitem(item, Money.wons(5_000_000L));
        auctionitemRepository.save(auctionitem1);

        Selling selling = new InstantSelling(newUser1, auctionitem);
        sellingRepository.save(selling);
    }

    @Test
    public void 판매_등록_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(1);
        //when
        Long save = sellingService.instantSave(user, auctionitem);
        Selling selling = sellingRepository.findById(save).get();
        //then
        assertThat(selling.getId()).isEqualTo(save);
        assertThat(selling.getSellingStatus()).isEqualTo(판매입찰중);
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
        Selling selling = new InstantSelling(user, auctionitem);
        Selling selling1 = new InstantSelling(user, auctionitem1);
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
        Selling selling = new InstantSelling(user, auctionitem);
        Selling selling1 = new InstantSelling(user, auctionitem1);
        sellingRepository.save(selling);
        sellingRepository.save(selling1);
        //when
        List<Selling> all = sellingService.findByItemName("맥북1");
        //then
        Assertions.assertThat(all.size()).isEqualTo(3);
        Assertions.assertThat(all).extracting("auctionitem").containsOnly(auctionitem, auctionitem1);
        Assertions.assertThat(all).extracting("seller").containsOnly(user);
    }

}