package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
        Item newItem = new Item("맥북", "serial1", 1_000_000L, 0, 1_000_000L, newCategory,newManufacturer);
        itemRepository.save(newItem);
        //when
        Item findItem = itemRepository.findById(newItem.getId()).get();

        //then
        assertThat(findItem).isEqualTo(newItem);
        assertThat(findItem.getId()).isEqualTo(newItem.getId());
        assertThat(findItem.getItemName()).isEqualTo(newItem.getItemName());
        assertThat(findItem.getModelNumber()).isEqualTo(newItem.getModelNumber());
        assertThat(findItem.getReleasePrice()).isEqualTo(newItem.getReleasePrice());
        assertThat(findItem.getMarketPrice()).isEqualTo(newItem.getMarketPrice());
        assertThat(findItem.getLikeCount()).isEqualTo(newItem.getLikeCount());
    }
}