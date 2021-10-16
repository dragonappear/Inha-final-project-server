package com.dragonappear.inha.domain.inspection.fail;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.inspection.Inspection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class FailInspection extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "fail_inspection_id")
    private Long id;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY,cascade = ALL, mappedBy = "failInspection")
    private FailDelivery failDelivery;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "inspection_id")
    private Inspection inspection;

    /**
     * 연관관계편의메서드
     */
    private void updateFailInspection(Inspection inspection) {
        this.inspection = inspection;
        inspection.updateFailInspection(this);
    }

    public void updateDelivery(FailDelivery failDelivery) {
        if (inspection != null) {
            this.failDelivery = failDelivery;
        }
    }

    /**
     * 생성자메서드
     */
    public FailInspection(Inspection inspection) {
        if (inspection != null) {
            updateFailInspection(inspection);
        }
    }

    /**
     * 비즈니스로직
     */

}