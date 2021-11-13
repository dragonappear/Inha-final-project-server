package com.dragonappear.inha.service.inspection.pass;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.pass.PassDelivery;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.InstantSelling;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.*;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import com.dragonappear.inha.repository.deal.DealRepository;
import com.dragonappear.inha.repository.inspection.InspectionRepository;
import com.dragonappear.inha.repository.inspection.pass.PassDeliveryRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.payment.PaymentRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
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
import static com.dragonappear.inha.domain.value.CourierName.*;

@SpringBootTest
@Transactional
@Rollback
class PassDeliveryServiceTest {
    @Autowired BuyingRepository buyingRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired UserAddressRepository userAddressRepository;
    @Autowired SellingRepository sellingRepository;
    @Autowired DealRepository dealRepository;
    @Autowired InspectionRepository inspectionRepository;
    @Autowired PassDeliveryRepository passDeliveryRepository;
    @Autowired PassDeliveryService passDeliveryService;

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

        Payment payment1 = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                ,bidAuctionitem.getPrice()
                ,Money.wons(0L)
                , user1
            ,1L,item);
        paymentRepository.save(payment1);

        Buying buying = new BidBuying(payment1,LocalDateTime.now());
        buyingRepository.save(buying);

        Deal deal = new Deal(buying, selling);
        dealRepository.save(deal);

        PassInspection passInspection = new PassInspection(deal);
        inspectionRepository.save(passInspection);
    }

    @Test
    public void 합격검수배송_생성_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        PassInspection passInspection = (PassInspection) inspectionRepository.findAll().get(0);
        PassDelivery passDelivery = new PassDelivery(new Delivery(CJ대한통운, "1234-1234")
                , passInspection.getDeal().getBuying().getPayment().getUser().getUserAddresses().get(0).getUserAddress()
                , passInspection);
        //when
        passDeliveryService.save(passDelivery);
        PassDelivery find = passDeliveryRepository.findById(passDelivery.getId()).get();
        //then
        Assertions.assertThat(find).isEqualTo(passDelivery);
        Assertions.assertThat(find.getDeliveryStatus()).isEqualTo(DeliveryStatus.배송시작);
        Assertions.assertThat(find.getBuyerAddress()).isEqualTo(user.getUserAddresses().get(0).getUserAddress());
        Assertions.assertThat(find.getDelivery()).isEqualTo(new Delivery(CJ대한통운, "1234-1234"));
    }

    @Test
    public void 합격검수배송_조회_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        PassInspection passInspection = (PassInspection) inspectionRepository.findAll().get(0);
        PassDelivery passDelivery = new PassDelivery(new Delivery(CJ대한통운, "1234-1234")
                , passInspection.getDeal().getBuying().getPayment().getUser().getUserAddresses().get(0).getUserAddress()
                , passInspection);
        passDeliveryRepository.save(passDelivery);
        //when
        PassDelivery find = passDeliveryService.findById(passDelivery.getId());
        //then
        Assertions.assertThat(find).isEqualTo(passDelivery);
        Assertions.assertThat(find.getDeliveryStatus()).isEqualTo(DeliveryStatus.배송시작);
        Assertions.assertThat(find.getBuyerAddress()).isEqualTo(user.getUserAddresses().get(0).getUserAddress());
        Assertions.assertThat(find.getDelivery()).isEqualTo(new Delivery(CJ대한통운, "1234-1234"));
    }
}