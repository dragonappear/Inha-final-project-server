package com.dragonappear.inha.domain.item;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Manufacturer extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false, updatable = false,unique = true)
    private ManufacturerName manufacturerName;

    /**
     * 연관관계
     */
    @OneToMany(mappedBy = "manufacturer")
    private List<Item> items = new ArrayList<>();

    /**
     * 연관관계편의메서드
     */

    /**
     * 생성자메서드
     */
    public Manufacturer(ManufacturerName manufacturerName) {
        this.manufacturerName = manufacturerName;
    }


}