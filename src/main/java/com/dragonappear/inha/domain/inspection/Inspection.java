package com.dragonappear.inha.domain.inspection;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.*;
import static lombok.AccessLevel.*;

@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public abstract class Inspection extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inspection_id")
    private Long id;


    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @OneToMany(mappedBy = "inspection",cascade = ALL)
    private List<InspectionImage> inspectionImages = new ArrayList<>();


    /**
     * 연관관계편의메서드
     */


    private void updateInspectionDeal(Deal deal, DealStatus status) {
        this.deal = deal;
        this.deal.updateDealStatus(status);
    }

    /**
     * 생성자메서드
     */
    public Inspection(Deal deal) {
        if (this instanceof PassInspection) {
            updateInspectionDeal(deal, DealStatus.검수합격);
        }
        else{
            updateInspectionDeal(deal, DealStatus.검수탈락);
        }
    }

}
