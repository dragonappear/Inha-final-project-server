package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.value.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback

class ItemRepositoryTest {
    @Autowired ItemRepository itemRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired CategoryRepository categoryRepository;

    @Test
    public void 아이템생성_테스트() throws Exception{
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        //given
        Item newItem = new Notebook("맥북", "serial1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                ,  Money.wons(1_000_000L),   newCategory,newManufacturer);
        itemRepository.save(newItem);
        //when
        Item findItem = itemRepository.findById(newItem.getId()).get();

        //then
        assertThat(findItem).isEqualTo(newItem);
        assertThat(findItem.getId()).isEqualTo(newItem.getId());
        assertThat(findItem.getItemName()).isEqualTo(newItem.getItemName());
        assertThat(findItem.getModelNumber()).isEqualTo(newItem.getModelNumber());
        assertThat(findItem.getReleasePrice()).isEqualTo(newItem.getReleasePrice());
        assertThat(findItem.getLatestPrice()).isEqualTo(newItem.getLatestPrice());
        assertThat(findItem.getLikeCount()).isEqualTo(newItem.getLikeCount());
    }
}