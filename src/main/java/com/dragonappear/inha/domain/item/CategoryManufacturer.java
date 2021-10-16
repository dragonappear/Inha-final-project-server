package com.dragonappear.inha.domain.item;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CategoryManufacturer extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_manufacturer_id")
    private Long id;

    /**
     * 연관관계
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * 생성자메서드
     */
    public CategoryManufacturer(Category category,Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        this.category = category;
    }
}
