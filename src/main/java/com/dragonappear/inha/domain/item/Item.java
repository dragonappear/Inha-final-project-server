package com.dragonappear.inha.domain.item;


import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Item extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false,updatable = false)
    private String itemName;

    @Column(nullable = false,updatable = false)
    private String modelNumber;

    @Column(nullable = false,updatable = false)
    private Long releasePrice;

    private Integer likeCount;

    private Long marketPrice;


    /**
     * 연관관계
     */

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImages = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<UserLikeItem> userLikeItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Auctionitem> auctionitems = new ArrayList<>();


    /**
     * 연관관계편의메서드
     */
    private void updateManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        manufacturer.getItems().add(this);
    }

    private void updateCategory(Category category) {
        this.category = category;
        category.getItems().add(this);
    }


    /**
     * 생성자메서드
     */
    public Item(String itemName, String modelNumber, Long releasePrice, Integer likeCount, Long marketPrice, Category category, Manufacturer manufacturer) {
        this.itemName = itemName;
        this.modelNumber = modelNumber;
        this.releasePrice = releasePrice;
        this.likeCount = likeCount;
        this.marketPrice = marketPrice;
        if(category!=null){
            updateCategory(category);
        }
        if (manufacturer != null) {
            updateManufacturer(manufacturer);
        }
    }
}
