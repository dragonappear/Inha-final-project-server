package com.dragonappear.inha.domain.auctionitem;


import com.dragonappear.inha.domain.JpaBaseEntity;
import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.value.Money;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.*;

@Inheritance(strategy = SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Auctionitem extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "auctionitem_id")
    private Long id;

    @Column(nullable = false)
    @Embedded
    private Money price;

    /**
     * 연관관계
     */
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @OneToOne(fetch = LAZY,mappedBy = "auctionitem")
    private Payment payment;

    @JsonIgnore
    @OneToOne(fetch = LAZY, mappedBy = "auctionitem")
    private Selling selling;

    /**
     * 연관관계편의메서드
     */
    private void updateAuctionItem(Item item) {
        this.item = item;
        item.getAuctionitems().add(this);
    }

    public void updateAuctionitemPayment(Payment payment) {
        this.payment = payment;
    }

    public void updateSellingAuctionitem(Selling selling) {
        this.selling = selling;
    }

    /**
     * 생성자메서드
     */
    public Auctionitem(Item item, Money price) {
        this.price = price;
        if (item != null) {
            updateAuctionItem(item);
        }
    }

}
