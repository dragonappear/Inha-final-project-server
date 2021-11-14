package com.dragonappear.inha.service.item;

import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.item.UserLikeItem;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import com.dragonappear.inha.repository.item.UserLikeItemRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserLikeItemServiceTest {
    @Autowired UserLikeItemRepository userLikeItemRepository;
    @Autowired UserLikeItemService userLikeItemService;
    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;

    @BeforeEach
    public void setUp() {


        User user = new User("name1", "nickname1", "email1@", "userTel11","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user);
        User user1 = new User("name2", "nickname2", "email2@", "userTel22","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user1);

        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);
        Item item = new Notebook("맥북1", "modelNumber1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버", Money.wons(10000L),
                category,manufacturer);
        Item item1 = new Notebook("맥북1", "modelNumber2", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버",Money.wons(10000L),
               category,manufacturer);
        itemRepository.save(item);
        itemRepository.save(item1);

        UserLikeItem userLikeItem = new UserLikeItem(item, user);
        userLikeItemRepository.save(userLikeItem);
        UserLikeItem userLikeItem1 = new UserLikeItem(item1, user);
        userLikeItemRepository.save(userLikeItem1);
    }

    @Test
    public void 유저아이템찜_생성_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(1);
        Item item = itemRepository.findAll().get(1);
        assertThat(item.getLikeCount()).isEqualTo(1L);
        UserLikeItem newLike = new UserLikeItem(item, user);
        //when
        userLikeItemService.save(newLike);
        UserLikeItem findLike = userLikeItemRepository.findById(newLike.getId()).get();
        //then
        assertThat(findLike).isEqualTo(newLike);
        assertThat(item.getLikeCount()).isEqualTo(2L);
    }

    @Test
    public void 유저아이템찜_취소_테스트() throws Exception{
        //given
        UserLikeItem find = userLikeItemRepository.findAll().get(0);
        Item item = find.getItem();
        assertThat(item.getLikeCount()).isEqualTo(1L);
        //when
        userLikeItemService.delete(find);
        List<UserLikeItem> all = userLikeItemRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(1);
        assertThat(item.getLikeCount()).isEqualTo(0L);
    }

    @Test
    public void 유저아이템찜조회_회원아이디로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        //when
        List<Item> all = userLikeItemService.findByUserId(user.getId());
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).extracting("modelNumber").containsOnly("modelNumber1", "modelNumber2");
    }
}