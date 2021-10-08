package com.dragonappear.inha.repository.payment;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
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
    public void 결제생성_테스트() throws Exception{
        //given
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        
        Item newItem = new Item("맥북", "serial1", 1_000_000L, 0, 1_000_000L, newCategory,newManufacturer);
        itemRepository.save(newItem);
        
        BidAuctionitem newBid = new BidAuctionitem(newItem,10_000_000_000L, AuctionitemStatus.경매진행, now(), of(now().getYear(), now().getMonth(), now().getDayOfMonth() + 1, now().getHour(), now().getMinute()));
        auctionitemRepository.save(newBid);
        
        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        userRepository.save(newUser);
        
        UserAddress newAddress = new UserAddress(newUser, new Address("incehon", "inharo", "127", "22207"));
        userAddressRepository.save(newAddress);
        
        Payment newPayment = new Payment(newBid.getItem().getItemName(), newBid.getPrice(), newUser.getUsername(), newUser.getEmail(), newUser.getUserTel(),
                newAddress.getUserAddress(), PaymentStatus.결제완료, newUser, newBid );
        paymentRepository.save(newPayment);
        
        //when
        Payment findPayment = paymentRepository.findById(newPayment.getId()).get();
        //then
        assertThat(findPayment).isEqualTo(newPayment);
        assertThat(findPayment.getId()).isEqualTo(newPayment.getId());
        assertThat(findPayment.getUser()).isEqualTo(newPayment.getUser());
        assertThat(findPayment.getAuctionitem()).isEqualTo(newPayment.getAuctionitem());
        assertThat(findPayment.getItemName()).isEqualTo(newPayment.getItemName());
        assertThat(findPayment.getPaymentPrice()).isEqualTo(newPayment.getPaymentPrice());
        assertThat(findPayment.getPaymentStatus()).isEqualTo(newPayment.getPaymentStatus());

    }

}