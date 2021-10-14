package com.dragonappear.inha;


import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.ItemImage;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import java.util.List;

import static com.dragonappear.inha.domain.item.value.CategoryName.노트북;
import static com.dragonappear.inha.domain.item.value.CategoryName.태블릿;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.삼성;
import static com.dragonappear.inha.domain.item.value.ManufacturerName.애플;

@Profile("init")
@RequiredArgsConstructor
@Component
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit0();
        initService.dbInit1();
        initService.dbInit2();
    }

    @RequiredArgsConstructor
    @Transactional
    @Component
    static class InitService {
        private final EntityManager em;
        private final CategoryRepository categoryRepository;
        private final ManufacturerRepository manufacturerRepository;

        public void dbInit0() {
            Category category = new Category(노트북);
            Category category1 = new Category(태블릿);
            em.persist(category);
            em.persist(category1);
            Manufacturer manufacturer = new Manufacturer(애플);
            Manufacturer manufacturer1 = new Manufacturer(삼성);
            em.persist(manufacturer);
            em.persist(manufacturer1);
        }


        public void dbInit1() {
            Category category = categoryRepository.findByCategoryName(노트북).get();
            Manufacturer manufacturer = manufacturerRepository.findByManufacturerName(애플).get();
            Item item1 = new Item("13인치 MacBook Pro", "modelNumber1", Money.wons(1_690_000),
                    Money.wons(1_400_000),category,manufacturer);
            Item item2 = new Item("13인치 MacBook Pro", "modelNumber2", Money.wons(1_690_000),
                    Money.wons(1_400_000),category,manufacturer);
            em.persist(item1);
            em.persist(item2);
            Image image1 = new Image("macbook", "img.png", "src/main/resources/static/items/img.png");
            ItemImage itemImage1 = new ItemImage(item1, image1);
            Image image2 = new Image("macbook", "img.png", "src/main/resources/static/items/img.png");
            ItemImage itemImage2 = new ItemImage(item2, image2);
            em.persist(itemImage1);
            em.persist(itemImage2);
        }
        public void dbInit2() {
            Category category = categoryRepository.findByCategoryName(노트북).get();
            Manufacturer manufacturer = manufacturerRepository.findByManufacturerName(삼성).get();
            Item item1 = new Item("Galaxy Book Pro", "modelNumber3", Money.wons(1_690_000),
                    Money.wons(1_400_000),category,manufacturer);
            Item item2 = new Item("Galaxy Book", "modelNumber4", Money.wons(1_690_000),
                    Money.wons(1_400_000),category,manufacturer);
            em.persist(item1);
            em.persist(item2);
            Image image1 = new Image("Galaxy Book Pro", "img.png", "src/main/resources/static/items/img.png");
            ItemImage itemImage1 = new ItemImage(item1, image1);
            Image image2 = new Image("Galaxy Book", "img.png", "src/main/resources/static/items/img.png");
            ItemImage itemImage2 = new ItemImage(item2, image2);
            em.persist(itemImage1);
            em.persist(itemImage2);
        }
    }
}
