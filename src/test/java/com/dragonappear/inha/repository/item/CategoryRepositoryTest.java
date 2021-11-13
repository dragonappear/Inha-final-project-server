package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.value.Category;
import com.dragonappear.inha.domain.item.value.CategoryName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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