package com.dragonappear.inha.service.payment;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
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
import java.util.List;
import java.util.Random;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static com.dragonappear.inha.domain.payment.value.PaymentStatus.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PaymentServiceTest {
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired PaymentService paymentService;
    @Autowired UserAddressRepository userAddressRepository;

    @BeforeEach
    public void setUp() {
        User user1 = new User("name1", "nickname1", "email1", "userTel1");
        userRepository.save(user1);
        UserAddress userAddress = new UserAddress(user1, new Address("yyh","010-1111-1111","city", "street", "detail", "zipcode"));
        userAddressRepository.save(userAddress);

        User user2 = new User("name2", "nickname2", "email2@", "userTel22");
        userRepository.save(user2);
        UserAddress userAddress2 = new UserAddress(user2, new Address("yyh","010-1111-1111","city", "street", "detail", "zipcode"));
        userAddressRepository.save(userAddress2);

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

        Auctionitem auctionitem2 = new Auctionitem(item, Money.wons(6_000_000L));
        auctionitemRepository.save(auctionitem2);

        Payment payment1 = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                , auctionitem1.getPrice()
                ,Money.wons(0L)
                , user1
                , 1L,item);
        Payment save1 = paymentRepository.save(payment1);
        Payment payment2 = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                , auctionitem1.getPrice()
                ,Money.wons(0L)
                , user1
                , 1L,item);
        Payment save2 = paymentRepository.save(payment2);
    }

    @Test
    public void 결제내역_생성_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(2);
        Payment payment = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                , auctionitem.getPrice()
                ,Money.wons(0L)
                , user
                , 1L,auctionitem.getItem());
        //when
        Long save = paymentService.save(payment);
        Payment findPayment = paymentRepository.findById(save).get();
        //then
        assertThat(findPayment.getId()).isEqualTo(save);
        assertThat(findPayment).isEqualTo(payment);
        assertThat(findPayment.getId()).isEqualTo(payment.getId());
        assertThat(findPayment.getId()).isEqualTo(save);
        assertThat(findPayment.getPaymentStatus()).isEqualTo(결제완료);
        assertThat(findPayment.getUser()).isEqualTo(payment.getUser());
        assertThat(findPayment.getAuctionitem()).isEqualTo(payment.getAuctionitem());
    }

    @Test
    public void 결제조회_결제아이디로_테스트()  throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        Auctionitem auctionitem = auctionitemRepository.findAll().get(2);
        Payment payment = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                , auctionitem.getPrice()
                ,Money.wons(0L)
                , user
                , 1L,auctionitem.getItem());
        Payment save = paymentRepository.save(payment);
        //when
        Payment findPayment = paymentService.findById(save.getId());
        //then
        assertThat(findPayment).isEqualTo(save);
        assertThat(findPayment.getId()).isEqualTo(save.getId());
        assertThat(findPayment).isEqualTo(save);
        assertThat(findPayment.getPaymentStatus()).isEqualTo(결제완료);
        assertThat(findPayment.getUser()).isEqualTo(save.getUser());
        assertThat(findPayment.getAuctionitem()).isEqualTo(save.getAuctionitem());
    }

    // 결제 조회 by 유저아이디
    @Test
    public void 결제조회_유저아이디로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        List<Payment> list = paymentRepository.findAll();
        //when
        List<Payment> all = paymentService.findByUserId(user.getId());
        //then
        assertThat(all).extracting("user").containsOnly(user);
        list.stream().forEach(payment -> {assertThat(all).contains(payment);});
    }


}