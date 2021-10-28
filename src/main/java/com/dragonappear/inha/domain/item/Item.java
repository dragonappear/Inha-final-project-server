package com.dragonappear.inha.domain.item;


import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.payment.BidPayment;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.value.Money;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static javax.persistence.InheritanceType.*;
import static lombok.AccessLevel.*;

@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public  class Item extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false,updatable = false)
    private String itemName;

    @Column(nullable = false,updatable = false)
    private LocalDate releaseDay;

    @Column(nullable = false,updatable = false)
    private String color;

    @Column(nullable = false,updatable = false,unique = true)
    private String modelNumber;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "releasePrice"))})
    @Column(nullable = false,updatable = false)
    @Embedded
    private Money releasePrice;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "latestPrice"))})
    @Embedded
    private Money latestPrice;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "lowestPrice"))})
    @Embedded
    private Money lowestPrice;

    @Column(nullable = false)
    private Long likeCount;

    /**
     * 연관관계
     */

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<UserLikeItem> userLikeItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<Auctionitem> auctionitems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<BidPayment> bidPayments = new ArrayList<>();

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
    public Item(String itemName, String modelNumber,LocalDate releaseDay,String color,Money releasePrice,Category category, Manufacturer manufacturer) {
        this.itemName = itemName;
        this.modelNumber = modelNumber;
        this.releasePrice = releasePrice;
        this.releaseDay = releaseDay;
        this.color = color;
        this.likeCount = 0L;
        this.latestPrice = null;
        this.lowestPrice = null;
        if(category!=null){
            updateCategory(category);
        }
        if (manufacturer != null) {
            updateManufacturer(manufacturer);
        }
    }

    /**
     * 비즈니스 로직
     */
    public Long like() {
        this.likeCount+=1;
        return this.likeCount;
    }

    public Long cancel() {
        if (likeCount - 1 < 0) {
            throw new IllegalStateException("회원아이템 찜은 0보다 커야합니다");
        }
        this.likeCount-=1;
        return this.likeCount;
    }

    public void updateLatestPrice(Money amount) {
        this.latestPrice = amount;
    }

    public void updateLowestPrice(Money amount){this.lowestPrice = amount;}

}
