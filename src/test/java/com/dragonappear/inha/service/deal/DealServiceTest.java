package com.dragonappear.inha.service.deal;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.InstantSelling;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static org.assertj.core.api.Assertions.*;

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
        UserAddress userAddress = new UserAddress(user1, new Address("yyh","010-1111-1111","city", "street", "detail", "zipcode"));
        userAddressRepository.save(userAddress);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Notebook("맥북1", "modelNumber1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                , Money.wons(10000L),
               category,manufacturer);
        itemRepository.save(item);

        Auctionitem bidAuctionitem = new Auctionitem(item, Money.wons(4_000_000L));
        auctionitemRepository.save(bidAuctionitem);

        Selling selling = new InstantSelling(user1, bidAuctionitem);
        sellingRepository.save(selling);

        Payment newPayment = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                ,bidAuctionitem.getPrice()
                ,Money.wons(0L)
                ,user1, 1L,item);
        paymentRepository.save(newPayment);

        Buying buying = new BidBuying(newPayment,LocalDateTime.now());
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