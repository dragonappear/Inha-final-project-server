package com.dragonappear.inha.service.payment;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
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
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.payment.PaymentRepository;
import com.dragonappear.inha.repository.selling.SellingDeliveryRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import com.dragonappear.inha.service.selling.SellingDeliveryService;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PaymentServiceTest {
    @Autowired SellingRepository sellingRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired SellingDeliveryRepository sellingDeliveryRepository;
    @Autowired SellingDeliveryService sellingDeliveryService;
    @Autowired PaymentRepository paymentRepository;
    @Autowired PaymentService paymentService;
    @Autowired UserAddressRepository userAddressRepository;

    @BeforeEach
    public void setUp() {
        User user1 = new User("name1", "nickname1", "email1", "userTel1");
        userRepository.save(user1);
        UserAddress userAddress = new UserAddress(user1, new Address("city", "street", "detail", "zipcode"));
        userAddressRepository.save(userAddress);

        User user2 = new User("name2", "nickname2", "email2@", "userTel22");
        userRepository.save(user2);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Item("맥북1", "modelNumber1", Money.wons(10000L),
                Money.wons(20000L),category,manufacturer);
        itemRepository.save(item);

        BidAuctionitem bidAuctionitem = new BidAuctionitem(item, Money.wons(4_000_000L), LocalDateTime.now().plusHours(1));
        auctionitemRepository.save(bidAuctionitem);

        Selling selling = new Selling(user2, bidAuctionitem);
        sellingRepository.save(selling);
    }

    @Test
    public void 결제내역_생성_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(0);
        Payment payment = new Payment(auctionitem.getItem().getItemName()
                , auctionitem.getPrice()
                , user.getUsername()
                , user.getEmail()
                , user.getUserTel()
                , user.getUserAddresses().get(0).getUserAddress()
                , user
                , auctionitem);
        //when
        Long save = paymentService.save(payment);
        Payment findPayment = paymentRepository.findById(save).get();
        //then
        Assertions.assertThat(findPayment.getId()).isEqualTo(save);
    }

    @Test
    public void 결제조회_결제아이디로_테스트()  throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(0);
        Payment payment = new Payment(auctionitem.getItem().getItemName()
                , auctionitem.getPrice()
                , user.getUsername()
                , user.getEmail()
                , user.getUserTel()
                , user.getUserAddresses().get(0).getUserAddress()
                , user
                , auctionitem);
        //when

        //then
    }
}