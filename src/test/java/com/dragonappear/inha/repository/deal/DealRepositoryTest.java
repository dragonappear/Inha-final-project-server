package com.dragonappear.inha.repository.deal;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.InstantSelling;
import com.dragonappear.inha.domain.selling.Selling;
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
import com.dragonappear.inha.repository.selling.SellingRepository;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class DealRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired SellingRepository sellingRepository;
    @Autowired AuctionitemRepository auctionitemRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired BuyingRepository buyingRepository;
    @Autowired UserAddressRepository userAddressRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired DealRepository dealRepository;

    @Test
    public void 거래생성_테스트() throws Exception{
        //given
        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        userRepository.save(newUser);
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        Item newItem = new Notebook("맥북", "serial1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                , Money.wons(1_000_000L),  newCategory,newManufacturer);
        itemRepository.save(newItem);
        Auctionitem newBid = new Auctionitem(newItem,Money.wons(10_000_000_000L));
        auctionitemRepository.save(newBid);
        Selling newSelling = new InstantSelling(newUser, newBid);
        sellingRepository.save(newSelling);
        UserAddress newAddress = new UserAddress(newUser, new Address("yyh","010-1111-1111","incehon", "inharo", "127", "22207"));
        userAddressRepository.save(newAddress);

        Payment newPayment = new Payment( "카카오페이"
                , "imp_"+ new Random().nextLong()
                ,"merchant_"+new Random().nextLong()
                ,newBid.getPrice()
                ,Money.wons(0L)
                , newUser
                , 1L,newItem);
        paymentRepository.save(newPayment);

        Buying newBuying = new BidBuying(newPayment, LocalDateTime.now());
        buyingRepository.save(newBuying);

        Deal newDeal = new Deal( newBuying, newSelling);
        dealRepository.save(newDeal);
        //when
        Deal findDeal = dealRepository.findById(newDeal.getId()).get();
        //then
        assertThat(findDeal).isEqualTo(newDeal);
        assertThat(findDeal.getId()).isEqualTo(newDeal.getId());
        assertThat(findDeal.getDealStatus()).isEqualTo(newDeal.getDealStatus());
        assertThat(findDeal.getBuying()).isEqualTo(newDeal.getBuying());
        assertThat(findDeal.getSelling()).isEqualTo(newDeal.getSelling());
    }
}