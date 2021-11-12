package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.ItemImage;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.domain.value.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ItemImageRepositoryTest {
    @Autowired ItemRepository itemRepository;
    @Autowired ItemImageRepository itemImageRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired CategoryRepository categoryRepository;
    @Test
    public void 아이템이미지생성_테스트() throws Exception{
        //given
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        Item newItem = new Notebook("맥북", "serial1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                ,  Money.wons(1_000_000L), newCategory,newManufacturer);
        itemRepository.save(newItem);
        Image image = new Image("file1", "origin1", "url1");
        ItemImage newImage = new ItemImage(newItem, image);
        itemImageRepository.save(newImage);
        //when
        ItemImage findImage = itemImageRepository.findById(newImage.getId()).get();
        //then
        assertThat(findImage).isEqualTo(newImage);
        assertThat(findImage.getId()).isEqualTo(newImage.getId());
        assertThat(findImage.getItem()).isEqualTo(newImage.getItem());
        assertThat(findImage.getItemImage().getFileName()).isEqualTo(newImage.getItemImage().getFileName());
        assertThat(findImage.getItemImage().getFileOriName()).isEqualTo(newImage.getItemImage().getFileOriName());
        assertThat(findImage.getItemImage().getFileUrl()).isEqualTo(newImage.getItemImage().getFileUrl());
    }
}