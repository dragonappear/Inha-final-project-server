package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.UserLikeItem;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserLikeItemRepositoryTest {
    @Autowired ItemRepository itemRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired UserRepository userRepository;
    @Autowired UserLikeItemRepository userLikeItemRepository;

    @Test
    public void 관심상품생성_테스트() throws Exception{
        //given
        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        userRepository.save(newUser);
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        Item newItem = new Item("맥북", "serial1", Money.wons(1_000_000L),  Money.wons(1_000_000L), newCategory,newManufacturer);
        itemRepository.save(newItem);
        UserLikeItem newLike = new UserLikeItem(newItem, newUser);
        userLikeItemRepository.save(newLike);
        //when
        UserLikeItem findLike = userLikeItemRepository.findById(newLike.getId()).get();
        //then
        assertThat(findLike).isEqualTo(newLike);
        assertThat(findLike.getId()).isEqualTo(newLike.getId());
        assertThat(findLike.getItem()).isEqualTo(newLike.getItem());
        assertThat(findLike.getUser()).isEqualTo(newLike.getUser());
    }
}