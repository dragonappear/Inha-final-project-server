package com.dragonappear.inha.domain.auctionitem;


import com.dragonappear.inha.domain.JpaBaseEntity;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.value.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.*;

@Inheritance(strategy = SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public abstract class Auctionitem extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "auctionitem_id")
    private Long id;

    @Column(nullable = false)
    @Embedded
    private Money price;

    @Enumerated(STRING)
    @Column(nullable = false)
    private AuctionitemStatus auctionitemStatus;

    @Column(nullable = false,updatable = false)
    protected LocalDateTime startDate;
    @Column(nullable = false,updatable = false)
    protected LocalDateTime endDate;


    /**
     * 연관관계
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = LAZY,mappedBy = "auctionitem")
    private Payment payment;

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
        this.auctionitemStatus = 거래중;
    }

    public void updateSellingAuctionitem(Selling selling) {
        this.selling = selling;
    }

    /**
     * 생성자메서드
     */
    public Auctionitem(Item item, Money price) {
        this.price = price;
        this.auctionitemStatus = 경매중;
        if (item != null) {
            updateAuctionItem(item);
        }
    }

    /**
     * 비즈니스 로직
     */

    public void updateStatus(AuctionitemStatus status) {
        this.auctionitemStatus = status;
    }
}
