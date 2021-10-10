package com.dragonappear.inha.repository.inspection;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.value.InspectionStatus;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import com.dragonappear.inha.repository.deal.DealRepository;
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

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class InspectionRepositoryTest {
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
        BidAuctionitem newBid = new BidAuctionitem(newItem,Money.wons(10_000_000_000L), of(now().getYear(), now().getMonth(), now().getDayOfMonth() + 1, now().getHour(), now().getMinute()));
        auctionitemRepository.save(newBid);
        Selling newSelling = new Selling(SellingStatus.판매중,newUser, newBid);
        sellingRepository.save(newSelling);
        UserAddress newAddress = new UserAddress(newUser, new Address("incehon", "inharo", "127", "22207"));
        userAddressRepository.save(newAddress);

        Payment newPayment = new Payment(newBid.getItem().getItemName(), newBid.getPrice(), newUser.getUsername(), newUser.getEmail(), newUser.getUserTel(),
                newAddress.getUserAddress(), PaymentStatus.결제완료, newUser, newBid );
        paymentRepository.save(newPayment);

        Buying newBuying = new Buying(BuyingStatus.구매중, newPayment);
        buyingRepository.save(newBuying);

        Deal newDeal = new Deal(DealStatus.거래진행, newBuying, newSelling);
        dealRepository.save(newDeal);
    }

    @Test
    public void 검수생성_테스트() throws Exception{
        //given
        Deal deal = dealRepository.findAll().get(0);
        Inspection newInspection = new Inspection(InspectionStatus.검수진행, deal);
        inspectionRepository.save(newInspection);
        //when
        Inspection findInspection = inspectionRepository.findById(newInspection.getId()).get();
        //then
        Assertions.assertThat(findInspection).isEqualTo(newInspection);
        Assertions.assertThat(findInspection.getId()).isEqualTo(newInspection.getId());
        Assertions.assertThat(findInspection.getInspectionStatus()).isEqualTo(newInspection.getInspectionStatus());
        Assertions.assertThat(findInspection.getDeal()).isEqualTo(newInspection.getDeal());
        Assertions.assertThat(findInspection.getPassInspection()).isNull();
        Assertions.assertThat(findInspection.getFailInspection()).isNull();
    }
}