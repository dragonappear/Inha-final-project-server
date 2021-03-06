package com.dragonappear.inha.repository.payment;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PaymentRepositoryTest {
    @Autowired PaymentRepository paymentRepository;
    @Autowired UserRepository userRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired UserAddressRepository userAddressRepository;

    @Test
    public void ????????????_?????????() throws Exception{
        //given
        Category newCategory = new Category(CategoryName.?????????);
        categoryRepository.save(newCategory);
        
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.??????);
        manufacturerRepository.save(newManufacturer);
        
        Item newItem = new Notebook("??????", "serial1", LocalDate.of(2021, 5, 21)
                ,"????????? ??????"
                ,  Money.wons(1_000_000L), newCategory,newManufacturer);
        itemRepository.save(newItem);

        Auctionitem newBid = new Auctionitem(newItem,Money.wons(10_000_000_000L));
        auctionitemRepository.save(newBid);
        
        User newUser = new User("?????????1", "yyh", "?????????1@naver.com","010-1234-5678","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("?????????")
                .build())));
        userRepository.save(newUser);
        
        UserAddress newAddress = new UserAddress(newUser, new Address("yyh","010-1111-1111","incehon", "inharo", "127", "22207"));
        userAddressRepository.save(newAddress);

        Payment newPayment = new Payment("???????????????"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                ,newBid.getPrice()
                ,Money.wons(0L)
                ,newUser, 1L,newItem);
        paymentRepository.save(newPayment);
        
        //when
        Payment findPayment = paymentRepository.findById(newPayment.getId()).get();
        //then
        assertThat(findPayment).isEqualTo(newPayment);
        assertThat(findPayment.getId()).isEqualTo(newPayment.getId());
        assertThat(findPayment.getUser()).isEqualTo(newPayment.getUser());
        assertThat(findPayment.getAuctionitem()).isEqualTo(newPayment.getAuctionitem());
        assertThat(findPayment.getPaymentPrice()).isEqualTo(newPayment.getPaymentPrice());
        assertThat(findPayment.getPaymentStatus()).isEqualTo(newPayment.getPaymentStatus());

    }

}