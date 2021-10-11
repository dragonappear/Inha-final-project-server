package com.dragonappear.inha.domain.inspection;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.failinspection.FailInspection;
import com.dragonappear.inha.domain.inspection.passinspection.PassInspection;
import com.dragonappear.inha.domain.inspection.value.InspectionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static com.dragonappear.inha.domain.inspection.value.InspectionStatus.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Inspection extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inspection_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(STRING)
    private InspectionStatus inspectionStatus;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY,mappedBy = "inspection")
    private PassInspection passInspection;

    @OneToOne(fetch = LAZY,mappedBy = "inspection")
    private com.dragonappear.inha.domain.inspection.failinspection.FailInspection failInspection;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @OneToMany(mappedBy = "inspection",cascade = ALL)
    private List<InspectionImage> inspectionImages = new ArrayList<>();


    /**
     * 연관관계편의메서드
     */

    public void updatePassInspection(PassInspection passInspection) {
        this.passInspection = passInspection;
        this.inspectionStatus = 검수합격;
    }

    public void updateFailInspection(FailInspection failInspection) {
        this.failInspection = failInspection;
        this.inspectionStatus = 검수탈락;
    }

    /**
     * 생성자메서드
     */
    public Inspection(Deal deal) {
        this.inspectionStatus = 검수진행;
        this.deal = deal;
    }

    /**
     * 비즈니스로직
     */

}
