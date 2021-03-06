package com.dragonappear.inha.domain.item.value;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.CategoryName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false, unique = true, updatable = false)
    private CategoryName categoryName;

    /**
     * 연관관계
     */

    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();


    /**
     * 연관관계편의메서드
     */

    /**
     * 생성자 메서드
     */

    public Category(CategoryName categoryName) {
        this.categoryName = categoryName;
    }
}