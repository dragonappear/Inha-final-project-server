package com.dragonappear.inha.service.deal;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import com.dragonappear.inha.repository.deal.DealRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.payment.PaymentRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import com.dragonappear.inha.service.auctionitem.BidAuctionItemService;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.selling.SellingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class DealServiceTest {
    @Autowired BuyingRepository buyingRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired UserAddressRepository userAddressRepository;
    @Autowired SellingRepository sellingRepository;
    @Autowired DealService dealService;
    @Autowired DealRepository dealRepository;

    @BeforeEach
    public void setUp() {
        User user1 = new User("name1", "nickname1", "email1", "userTel1");
        userRepository.save(user1);
        UserAddress userAddress = new UserAddress(user1, new Address("city", "street", "detail", "zipcode"));
        userAddressRepository.save(userAddress);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Item("맥북1", "modelNumber1", Money.wons(10000L),
                Money.wons(20000L),category,manufacturer);
        itemRepository.save(item);

        BidAuctionitem bidAuctionitem = new BidAuctionitem(item, Money.wons(4_000_000L), LocalDateTime.now().plusHours(1));
        auctionitemRepository.save(bidAuctionitem);

        Selling selling = new Selling(user1, bidAuctionitem);
        sellingRepository.save(selling);

        Payment payment1 = new Payment(bidAuctionitem.getItem().getItemName()
                , bidAuctionitem.getPrice()
                , user1.getUsername()
                , user1.getEmail()
                , user1.getUserTel()
                , user1.getUserAddresses().get(0).getUserAddress()
                , user1
                , bidAuctionitem);
        paymentRepository.save(payment1);

        Buying buying = new Buying(payment1);
        buyingRepository.save(buying);
    }

    @Test
    public void 거래_생성_테스트() throws Exception{
        //given
        Buying buying = buyingRepository.findAll().get(0);
        Selling selling = sellingRepository.findAll().get(0);
        Deal deal = new Deal(buying, selling);
        //when
        Long save = dealService.save(deal);
        Deal find = dealRepository.findById(save).get();
        //then
        assertThat(find).isEqualTo(deal);
        assertThat(find.getId()).isEqualTo(save);
        assertThat(find.getBuying()).isEqualTo(buying);
        assertThat(find.getSelling()).isEqualTo(selling);
    }

    @Test
    public void 거래조회_거래아이디로_테스트() throws Exception{
        //given
        Buying buying = buyingRepository.findAll().get(0);
        Selling selling = sellingRepository.findAll().get(0);
        Deal deal = new Deal(buying, selling);
        dealRepository.save(deal);
        //when
        Deal find = dealService.findById(deal.getId());
        //then
        assertThat(find).isEqualTo(deal);
        assertThat(find.getId()).isEqualTo(deal.getId());
        assertThat(find.getBuying()).isEqualTo(buying);
        assertThat(find.getSelling()).isEqualTo(selling);
    }
}