package com.dragonappear.inha.service.inspection;

import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.passinspection.PassInspection;
import com.dragonappear.inha.domain.inspection.value.InspectionStatus;
import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import com.dragonappear.inha.repository.deal.DealRepository;
import com.dragonappear.inha.repository.inspection.InspectionRepository;
import com.dragonappear.inha.repository.inspection.passinspection.PassInspectionRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PassInspectionServiceTest {
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
    @Autowired PassInspectionRepository passInspectionRepository;
    @Autowired PassInspectionService passInspectionService;

    @BeforeEach
    public void setUp() {
        User user1 = new User("name1", "nickname1", "email1", "userTel1");
        userRepository.save(user1);
        UserAddress userAddress = new UserAddress(user1, new Address("city", "street", "detail", "zipcode"));
        userAddressRepository.save(userAddress);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);

        Item item = new Item("맥북1", "modelNumber1", Money.wons(10000L),
                Money.wons(20000L),category,manufacturer);
        itemRepository.save(item);

        BidAuctionitem bidAuctionitem = new BidAuctionitem(item, Money.wons(4_000_000L), LocalDateTime.now().plusHours(1));
        auctionitemRepository.save(bidAuctionitem);

        Selling selling = new Selling(user1, bidAuctionitem);
        sellingRepository.save(selling);

        Payment payment1 = new Payment(bidAuctionitem.getItem().getItemName()
                , bidAuctionitem.getPrice()
                , user1.getUsername()
                , user1.getEmail()
                , user1.getUserTel()
                , user1.getUserAddresses().get(0).getUserAddress()
                , user1
                , bidAuctionitem);
        paymentRepository.save(payment1);

        Buying buying = new Buying(payment1);
        buyingRepository.save(buying);

        Deal deal = new Deal(buying, selling);
        dealRepository.save(deal);

        Inspection newInspection = new Inspection(deal);
        inspectionRepository.save(newInspection);
    }

    @Test
    public void 검수합격_생성_테스트() throws Exception{
        //given
        Inspection inspection = inspectionRepository.findAll().get(0);
        PassInspection passInspection = new PassInspection(inspection);
        //when
        passInspectionService.save(passInspection);
        PassInspection find = passInspectionRepository.findById(passInspection.getId()).get();
        //then
        assertThat(find).isEqualTo(passInspection);
        assertThat(find.getId()).isEqualTo(passInspection.getId());
        assertThat(find.getInspection()).isEqualTo(passInspection.getInspection());
        assertThat(inspection.getInspectionStatus()).isEqualTo(InspectionStatus.검수합격);
        assertThat(inspection.getDeal().getDealStatus()).isEqualTo(DealStatus.거래완료);
        assertThat(inspection.getDeal().getSelling().getSellingStatus()).isEqualTo(SellingStatus.판매완료);
        assertThat(inspection.getDeal().getSelling().getAuctionitem().getAuctionitemStatus()).isEqualTo(AuctionitemStatus.경매완료);
        assertThat(inspection.getDeal().getBuying().getBuyingStatus()).isEqualTo(BuyingStatus.구매완료);
    }

    @Test
    public void 검수합격_조회_테스트() throws Exception{
        //given
        Inspection inspection = inspectionRepository.findAll().get(0);
        PassInspection passInspection = new PassInspection(inspection);
        passInspectionRepository.save(passInspection);
        //when
        PassInspection find = passInspectionService.findById(passInspection.getId());
        //then
        assertThat(find).isEqualTo(passInspection);
    }
}