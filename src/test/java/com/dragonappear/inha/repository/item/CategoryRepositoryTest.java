package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class CategoryRepositoryTest {
    @Autowired CategoryRepository categoryRepository;
    @Test
    public void 카테고리생성_테스트() throws Exception{
        //given
        Category newCategory = new Category(CategoryName.노트북);
        categoryRepository.save(newCategory);
        //when
        Category findCategory = categoryRepository.findById(newCategory.getId()).get();
        //then
        assertThat(findCategory).isEqualTo(newCategory);
        assertThat(findCategory.getId()).isEqualTo(newCategory.getId());
        assertThat(findCategory.getCategoryName()).isEqualTo(newCategory.getCategoryName());
    }
}