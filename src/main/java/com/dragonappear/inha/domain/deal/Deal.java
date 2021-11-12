package com.dragonappear.inha.domain.deal;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.dragonappear.inha.domain.deal.value.DealStatus.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Deal extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "deal_id")
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private DealStatus dealStatus;

    /**
     * 연관관계
     */


    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "buying_id", nullable = false)
    private Buying buying;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "selling_id", nullable = false)
    private Selling selling;

    @OneToOne(fetch = LAZY,mappedBy = "deal")
    private Inspection inspection;

    /**
     * 연관관계메서드
     */
    private void updateBuying(Buying buying) {
        buying.updateDeal(this);
        buying.updateStatus(BuyingStatus.거래진행);
    }

    private void updateSelling(Selling selling) {
        selling.updateDeal(this);
        selling.getAuctionitem().getItem().updateLatestPrice(this.selling.getAuctionitem().getPrice());
        selling.updateStatus(SellingStatus.거래진행);
    }

    /**
     * 생성자메서드
     */
    public Deal(Buying buying, Selling selling) {
        this.buying = buying;
        this.selling = selling;
        updateDealStatus(거래진행);
    }

    /**
     * 비즈니스로직
     */
    public void updateDealStatus(DealStatus dealStatus) {
        this.dealStatus = dealStatus;
        updateBuyingAndSellingStatus(dealStatus);
    }

    private void updateBuyingAndSellingStatus(DealStatus dealStatus) {
        if (dealStatus == 거래진행) {
            updateBuying(buying);
            updateSelling(selling);
        } else if (dealStatus == 입고완료) {
            this.buying.updateStatus(BuyingStatus.입고완료);
            this.selling.updateStatus(SellingStatus.입고완료);
        } else if (dealStatus == 검수진행) {
            this.buying.updateStatus(BuyingStatus.검수진행);
            this.selling.updateStatus(SellingStatus.검수진행);
        } else if (dealStatus == 검수합격) {
            this.buying.updateStatus(BuyingStatus.검수합격);
            this.selling.updateStatus(SellingStatus.검수합격);
        } else if (dealStatus == 검수탈락) {
            this.buying.updateStatus(BuyingStatus.검수탈락);
            this.selling.updateStatus(SellingStatus.검수탈락);
        } else if (dealStatus == 미입고취소) {
            this.buying.updateStatus(BuyingStatus.미입고취소);
            this.selling.updateStatus(SellingStatus.미입고취소);
        } else if (dealStatus == 검수탈락취소) {
            this.buying.updateStatus(BuyingStatus.검수탈락취소);
            this.selling.updateStatus(SellingStatus.검수탈락취소);
        }
    }

}
