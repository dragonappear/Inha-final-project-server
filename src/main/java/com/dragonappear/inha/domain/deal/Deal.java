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
    @JoinColumn(name = "buying_id")
    private Buying buying;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "selling_id")
    private Selling selling;

    @OneToOne(fetch = LAZY,mappedBy = "deal")
    private Inspection inspection;

    /**
     * 연관관계메서드
     */


    /**
     * 생성자메서드
     */
    public Deal(Buying buying, Selling selling) {
        this.dealStatus = 거래진행;
        if (buying != null) {
            updateBuying(buying);
        }
        if (selling != null) {
            updateSelling(selling);
        }
    }

    /**
     * 비즈니스로직
     */
    public void updateDealStatus(DealStatus dealStatus) {
        this.dealStatus = dealStatus;
        if(dealStatus== 거래취소){
            this.buying.updateStatus(BuyingStatus.구매취소);
            this.selling.updateStatus(SellingStatus.판매취소);
        }
        else if (dealStatus == 검수완료) {
            this.buying.updateStatus(BuyingStatus.구매완료);
            this.selling.updateStatus(SellingStatus.판매완료);
        }
    }

    private void updateBuying(Buying buying) {
        this.buying = buying;
        buying.updateStatus(BuyingStatus.거래중);
    }

    private void updateSelling(Selling selling) {
        this.selling = selling;
        this.selling.getAuctionitem().getItem().updateLatestPrice(this.selling.getAuctionitem().getPrice());
        selling.updateStatus(SellingStatus.거래중);
    }
}
