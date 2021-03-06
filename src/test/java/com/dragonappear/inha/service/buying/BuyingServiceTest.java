package com.dragonappear.inha.service.buying;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.payment.PaymentRepository;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class BuyingServiceTest {
    @Autowired BuyingService buyingService;
    @Autowired BuyingRepository buyingRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired UserAddressRepository userAddressRepository;


    @BeforeEach
    public void setUp() {
        User user1 = new User("name1", "nickname1", "email1", "userTel1","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user1);
        UserAddress userAddress = new UserAddress(user1, new Address("yyh","010-1111-1111","city", "street", "detail", "zipcode"));
        userAddressRepository.save(userAddress);

        User user2 = new User("name2", "nickname2", "email2@", "userTel22","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user2);
        UserAddress userAddress2 = new UserAddress(user2, new Address("yyh","010-1111-1111","city", "street", "detail", "zipcode"));
        userAddressRepository.save(userAddress2);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Notebook("맥북1", "modelNumber1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                , Money.wons(10000L)
                ,category,manufacturer);
        itemRepository.save(item);

        Auctionitem bidAuctionitem = new Auctionitem(item, Money.wons(4_000_000L));
        auctionitemRepository.save(bidAuctionitem);

        Auctionitem bidAuctionitem1 = new Auctionitem(item, Money.wons(5_000_000L));
        auctionitemRepository.save(bidAuctionitem1);

        Auctionitem bidAuctionitem2 = new Auctionitem(item, Money.wons(6_000_000L));
        auctionitemRepository.save(bidAuctionitem2);

        Payment payment1 = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                , bidAuctionitem.getPrice()
                ,Money.wons(0L)
                , user1
                , 1L,item);
        Payment save1 = paymentRepository.save(payment1);
        Payment payment2 = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                , bidAuctionitem.getPrice()
                ,Money.wons(0L)
                , user1
                , 1L,item);
        Payment save2 = paymentRepository.save(payment2);
    }

    // 구매내역 생성
    @Test
    public void 구매내역_생성_테스트() throws Exception{
        //given
        Payment payment = paymentRepository.findAll().get(0);
        Buying buying = new BidBuying(payment,LocalDateTime.now());
        //when
        Long save = buyingService.save(buying);
        Buying findBuying = buyingRepository.findById(save).get();
        //then
        assertThat(findBuying).isEqualTo(buying);
        assertThat(findBuying.getBuyingStatus()).isEqualTo(BuyingStatus.구매입찰중);
    }

    // 구매내역리스트 조회 by 유저아이디
    @Test
    public void 구매내역조회_유저아이디로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Payment payment = paymentRepository.findAll().get(0);
        Payment payment1 = paymentRepository.findAll().get(1);
        Buying buying = new BidBuying(payment,LocalDateTime.now());
        Buying buying1 =new BidBuying(payment1,LocalDateTime.now());
        buyingRepository.save(buying);
        buyingRepository.save(buying1);
        //when
        List<Buying> all = buyingService.findByUserId(user.getId());
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).containsOnly(buying, buying1);
    }

    // 구매내역 조회 by 구매아이디
    @Test
    public void 구매내역조회_구매아이디로_테스트() throws Exception{
        //given
        Payment payment = paymentRepository.findAll().get(0);
        Buying buying = new BidBuying(payment,LocalDateTime.now());
        buyingRepository.save(buying);
        //when
        Buying find = buyingService.findById(buying.getId());
        //then
        assertThat(find).isEqualTo(buying);
        assertThat(find.getId()).isEqualTo(buying.getId());
        assertThat(find.getBuyingStatus()).isEqualTo(BuyingStatus.구매입찰중);
    }
}