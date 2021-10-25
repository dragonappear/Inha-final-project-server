package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.Random;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class BuyingRepositoryTest {
    @Autowired PaymentRepository paymentRepository;
    @Autowired UserRepository userRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired UserAddressRepository userAddressRepository;
    @Autowired BuyingRepository buyingRepository;

    @Test
    public void 구매생성_테스트() throws Exception{
        //given
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);

        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);

        Item newItem = new Item("맥북", "serial1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                ,  Money.wons(1_000_000L),   newCategory,newManufacturer);
        itemRepository.save(newItem);

        BidAuctionitem newBid = new BidAuctionitem(newItem,Money.wons(10_000_000_000L),  of(now().getYear(), now().getMonth(), now().getDayOfMonth() + 1, now().getHour(), now().getMinute()));
        auctionitemRepository.save(newBid);

        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        userRepository.save(newUser);

        UserAddress newAddress = new UserAddress(newUser, new Address("yyh","010-1111-1111","incehon", "inharo", "127", "22207"));
        userAddressRepository.save(newAddress);

        Payment newPayment = new Payment("카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                ,newBid.getPrice()
                ,Money.wons(0L)
                ,newUser, newBid );
        paymentRepository.save(newPayment);

        Buying newBuying = new Buying(newPayment);
        buyingRepository.save(newBuying);
        //when
        Buying findBuying = buyingRepository.findById(newBuying.getId()).get();
        //then

        assertThat(findBuying).isEqualTo(newBuying);
        assertThat(findBuying.getId()).isEqualTo(newBuying.getId());
        assertThat(findBuying.getBuyingStatus()).isEqualTo(newBuying.getBuyingStatus());
        assertThat(findBuying.getPayment()).isEqualTo(newBuying.getPayment());
    }

}