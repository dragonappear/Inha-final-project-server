package com.dragonappear.inha.repository.inspection.pass;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.pass.PassDelivery;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.*;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import com.dragonappear.inha.repository.deal.DealRepository;
import com.dragonappear.inha.repository.inspection.InspectionRepository;
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

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PassDeliveryRepositoryTest {

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
    @Autowired InspectionRepository inspectionRepository;
    @Autowired PassInspectionRepository passInspectionRepository;
    @Autowired PassDeliveryRepository passDeliveryRepository;

    @BeforeEach
    void setUp() {
        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        userRepository.save(newUser);
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        Item newItem = new Item("맥북", "serial1",  Money.wons(1_000_000L),  Money.wons(1_000_000L), newCategory,newManufacturer);
        itemRepository.save(newItem);
        BidAuctionitem newBid = new BidAuctionitem(newItem,Money.wons(10_000_000_000L),of(now().getYear(), now().getMonth(), now().getDayOfMonth() + 1, now().getHour(), now().getMinute()));
        auctionitemRepository.save(newBid);
        Selling newSelling = new Selling(newUser, newBid);
        sellingRepository.save(newSelling);
        UserAddress newAddress = new UserAddress(newUser, new Address("incehon", "inharo", "127", "22207"));
        userAddressRepository.save(newAddress);
        Payment newPayment = new Payment(newBid.getItem().getItemName(), newBid.getPrice(), newUser.getUsername(), newUser.getEmail(), newUser.getUserTel(),
                newAddress.getUserAddress(), newUser, newBid );
        paymentRepository.save(newPayment);
        Buying newBuying = new Buying(newPayment);
        buyingRepository.save(newBuying);
        Deal newDeal = new Deal( newBuying, newSelling);
        dealRepository.save(newDeal);
        Inspection newInspection = new Inspection(newDeal);
        inspectionRepository.save(newInspection);
        PassInspection passInspection = new PassInspection(newInspection);
        passInspectionRepository.save(passInspection);
    }

    @Test
    public void 합격검수배송생성_테스트() throws Exception{
        //given
        PassInspection passInspection = passInspectionRepository.findAll().get(0);
        PassDelivery newDelivery = new PassDelivery(new Delivery(CourierName.CJ대한통운, "123456789"),
                new Address("city", "street", "detail", "zipcode"),
                passInspection);
        passDeliveryRepository.save(newDelivery);
        //when
        PassDelivery findDelivery = passDeliveryRepository.findById(newDelivery.getId()).get();
        //then
        assertThat(findDelivery).isEqualTo(newDelivery);
        assertThat(findDelivery.getId()).isEqualTo(newDelivery.getId());
        assertThat(findDelivery.getDelivery()).isEqualTo(newDelivery.getDelivery());
        assertThat(findDelivery.getDeliveryStatus()).isEqualTo(newDelivery.getDeliveryStatus());
        assertThat(findDelivery.getBuyerAddress()).isEqualTo(newDelivery.getBuyerAddress());
        assertThat(findDelivery.getPassInspection()).isEqualTo(newDelivery.getPassInspection());
        assertThat(passInspection.getPassDelivery()).isEqualTo(findDelivery);
    }
}