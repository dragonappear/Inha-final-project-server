package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
