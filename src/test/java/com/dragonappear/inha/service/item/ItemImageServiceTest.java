package com.dragonappear.inha.service.item;

import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.ItemImage;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ItemImageRepository;
import com.dragonappear.inha.repository.item.ItemRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ItemImageServiceTest {
    @Autowired ItemImageRepository itemImageRepository;
    @Autowired ItemImageService itemImageService;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ManufacturerRepository manufacturerRepository;
    @Autowired ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        Category category = new Category(노트북);
        Manufacturer manufacturer = new Manufacturer(삼성);
        categoryRepository.save(category);
        manufacturerRepository.save(manufacturer);
        Item item = new Item("맥북1", "modelNumber1", LocalDate.of(2021, 5, 21)
                ,"미스틱 실버"
                , Money.wons(10000L),
                Money.wons(20000L),category,manufacturer);
        itemRepository.save(item);
    }

    @Test
    public void 아이템이미지_생성_테스트() throws Exception {
        //given
        Item item = itemRepository.findAll().get(0);
        Image image = new Image("filename1", "filename1", "fileurl1");
        ItemImage itemImage = new ItemImage(item, image);
        //when
        itemImageService.update(itemImage);
        ItemImage findImage = itemImageRepository.findById(itemImage.getId()).get();
        //then
        assertThat(findImage).isEqualTo(itemImage);
        assertThat(findImage.getId()).isEqualTo(itemImage.getId());
        assertThat(findImage.getItemImage()).isEqualTo(itemImage.getItemImage());
        assertThat(findImage.getItem()).isEqualTo(itemImage.getItem());
    }

    @Test
    public void 아이템이미지조회_유저아이디로_테스트() throws Exception{
        //given
        Item item = itemRepository.findAll().get(0);
        Image image1 = new Image("filename1", "filename1", "fileurl1");
        Image image2 = new Image("filename2", "filename2", "fileurl2");
        Image image3 = new Image("filename3", "filename3", "fileurl3");
        ItemImage itemImage1 = new ItemImage(item, image1);
        ItemImage itemImage2 = new ItemImage(item, image2);
        ItemImage itemImage3 = new ItemImage(item, image3);
        itemImageRepository.save(itemImage1);
        itemImageRepository.save(itemImage2);
        itemImageRepository.save(itemImage3);
        //when
        List<ItemImage> all = itemImageService.findByItemId(item.getId());
        //then
        assertThat(all).containsOnly(itemImage1, itemImage2, itemImage3);
    }

    @Test
    public void 아이템이미지_중복등록_테스트() throws Exception{
        //given
        Item item = itemRepository.findAll().get(0);
        Image image1 = new Image("filename1", "filename1", "fileurl1");
        Image image2 = new Image("filename2", "filename1", "fileurl2");
        ItemImage itemImage1 = new ItemImage(item, image1);
        ItemImage itemImage2 = new ItemImage(item, image2);
        //when
        itemImageService.update(itemImage1);
        //then
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class
                , () -> {
                    itemImageService.update(itemImage2);
                }
        );
    }

    @Test
    public void 아이템이미지_단건삭제_테스트() throws Exception{
        //given
        Item item = itemRepository.findAll().get(0);
        Image image1 = new Image("filename1", "filename1", "fileurl1");
        Image image2 = new Image("filename2", "filename2", "fileurl2");
        Image image3 = new Image("filename3", "filename3", "fileurl3");
        ItemImage itemImage1 = new ItemImage(item, image1);
        ItemImage itemImage2 = new ItemImage(item, image2);
        ItemImage itemImage3 = new ItemImage(item, image3);
        itemImageRepository.save(itemImage1);
        itemImageRepository.save(itemImage2);
        itemImageRepository.save(itemImage3);
        //when
        itemImageService.deleteOne(itemImage1);
        List<ItemImage> all = itemImageRepository.findByItemId(item.getId());
        //then
        assertThat(all).containsOnly(itemImage2, itemImage3);
    }
}