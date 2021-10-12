package com.dragonappear.inha.service.selling;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.SellingDelivery;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.CourierName;
import com.dragonappear.inha.domain.value.Delivery;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.selling.SellingDeliveryRepository;
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
import static com.dragonappear.inha.domain.value.CourierName.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class SellingDeliveryServiceTest {
    @Autowired SellingRepository sellingRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired SellingDeliveryRepository sellingDeliveryRepository;
    @Autowired SellingDeliveryService sellingDeliveryService;

    @BeforeEach
    public void setUp() {
        User newUser1 = new User("name1", "nickname1", "email1@", "userTel11");
        userRepository.save(newUser1);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Item("맥북1", "modelNumber1", Money.wons(10000L),
                Money.wons(20000L),category,manufacturer);
        itemRepository.save(item);

        BidAuctionitem bidAuctionitem = new BidAuctionitem(item, Money.wons(4_000_000L), LocalDateTime.now().plusHours(1));
        auctionitemRepository.save(bidAuctionitem);

        Selling selling = new Selling(newUser1, bidAuctionitem);
        sellingRepository.save(selling);
    }

    @Test
    public void 판매자배송_생성_테스트() throws Exception{
        //given
        Selling selling = sellingRepository.findAll().get(0);
        Delivery delivery = new Delivery(CJ대한통운, "1234-1234");
        //when
        Long save = sellingDeliveryService.save(selling, delivery);
        SellingDelivery sellingDelivery = sellingDeliveryRepository.findById(save).get();
        //then
        assertThat(sellingDelivery.getId()).isEqualTo(save);
        assertThat(sellingDelivery.getDelivery()).isEqualTo(delivery);
        assertThat(sellingDelivery.getSelling()).isEqualTo(selling);
        assertThat(selling.getSellingDelivery()).isNotNull();
        assertThat(selling.getSellingDelivery().getDelivery().getCourierName()).isEqualTo(CJ대한통운);
        assertThat(selling.getSellingDelivery().getDelivery().getInvoiceNumber()).isEqualTo("1234-1234");
    }

    @Test
    public void 판매자배송_수정_테스트() throws Exception{
        //given
        Selling selling = sellingRepository.findAll().get(0);
        Delivery delivery = new Delivery(CJ대한통운, "1234-1234");
        Delivery delivery1 = new Delivery(CJ대한통운, "1234-5678");
        Long save = sellingDeliveryService.save(selling, delivery);
        //when
        sellingDeliveryService.update(selling, delivery1);
        SellingDelivery sellingDelivery = sellingDeliveryRepository.findById(save).get();
        //then
        assertThat(sellingDelivery).isEqualTo(selling.getSellingDelivery());
        assertThat(sellingDelivery.getDelivery().getCourierName()).isEqualTo(CJ대한통운);
        assertThat(sellingDelivery.getDelivery().getInvoiceNumber()).isEqualTo("1234-5678");
        assertThat(sellingDelivery.getSelling()).isEqualTo(selling);
    }

}