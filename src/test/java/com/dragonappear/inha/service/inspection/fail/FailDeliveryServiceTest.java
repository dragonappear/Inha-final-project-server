package com.dragonappear.inha.service.inspection.fail;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.fail.FailDelivery;
import com.dragonappear.inha.domain.inspection.fail.FailInspection;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Delivery;
import com.dragonappear.inha.domain.value.DeliveryStatus;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import com.dragonappear.inha.repository.deal.DealRepository;
import com.dragonappear.inha.repository.inspection.InspectionRepository;
import com.dragonappear.inha.repository.inspection.fail.FailDeliveryRepository;
import com.dragonappear.inha.repository.inspection.fail.FailInspectionRepository;
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
import static com.dragonappear.inha.domain.value.CourierName.CJ대한통운;

@SpringBootTest
@Transactional
@Rollback
class FailDeliveryServiceTest {
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
    @Autowired FailInspectionRepository failInspectionRepository;
    @Autowired FailDeliveryRepository failDeliveryRepository;
    @Autowired FailDeliveryService failDeliveryService;

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

        Item item = new Item("맥북1", "modelNumber1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                , Money.wons(10000L),
                category,manufacturer);
        itemRepository.save(item);

        BidAuctionitem bidAuctionitem = new BidAuctionitem(item, Money.wons(4_000_000L), LocalDateTime.now().plusHours(1));
        auctionitemRepository.save(bidAuctionitem);

        Selling selling = new Selling(user1, bidAuctionitem);
        sellingRepository.save(selling);

        Payment payment1 = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                ,bidAuctionitem.getPrice()
                ,Money.wons(0L)
                , user1
                , bidAuctionitem,1L);
        paymentRepository.save(payment1);

        Buying buying = new Buying(payment1);
        buyingRepository.save(buying);

        Deal deal = new Deal(buying, selling);
        dealRepository.save(deal);

        Inspection newInspection = new Inspection(deal);
        inspectionRepository.save(newInspection);

        FailInspection failInspection = new FailInspection(newInspection);
        failInspectionRepository.save(failInspection);
    }
    
    @Test
    public void 탈락검수배송_생성_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        FailInspection failInspection = failInspectionRepository.findAll().get(0);
        FailDelivery failDelivery = new FailDelivery(new Delivery(CJ대한통운, "1234-1234")
                , failInspection.getInspection().getDeal().getSelling().getSeller().getUserAddresses().get(0).getUserAddress()
                , failInspection);
        //when
        failDeliveryService.save(failDelivery);
        FailDelivery find = failDeliveryRepository.findById(failDelivery.getId()).get();
        //then
        Assertions.assertThat(find).isEqualTo(failDelivery);
        Assertions.assertThat(find.getDeliveryStatus()).isEqualTo(DeliveryStatus.배송시작);
        Assertions.assertThat(find.getSellerAddress()).isEqualTo(user.getUserAddresses().get(0).getUserAddress());
        Assertions.assertThat(find.getDelivery()).isEqualTo(new Delivery(CJ대한통운, "1234-1234"));
    }

    @Test
    public void 탈락검수배송_조회_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        FailInspection failInspection = failInspectionRepository.findAll().get(0);
        FailDelivery failDelivery = new FailDelivery(new Delivery(CJ대한통운, "1234-1234")
                , failInspection.getInspection().getDeal().getSelling().getSeller().getUserAddresses().get(0).getUserAddress()
                , failInspection);
        failDeliveryRepository.save(failDelivery);
        //when
        FailDelivery find = failDeliveryService.findById(failDelivery.getId());
        //then
        Assertions.assertThat(find).isEqualTo(failDelivery);
        Assertions.assertThat(find.getDeliveryStatus()).isEqualTo(DeliveryStatus.배송시작);
        Assertions.assertThat(find.getSellerAddress()).isEqualTo(user.getUserAddresses().get(0).getUserAddress());
        Assertions.assertThat(find.getDelivery()).isEqualTo(new Delivery(CJ대한통운, "1234-1234"));
    }
}