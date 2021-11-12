package com.dragonappear.inha.domain.inspection;


import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class InspectionImage extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inspection_image_id")
    private Long id;

    @Column(nullable = false)
    @Embedded
    private Image inspectionImage;

    /**
     * 연관관계
     */

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "inspection_id")
    private Inspection inspection;

    /**
     * 연관관계편의메서드
     */

    private void updateInspectionImage(Inspection inspection) {
        this.inspection = inspection;
        inspection.getInspectionImages().add(this);
    }

    /**
     * 생성자메서드
     */

    public InspectionImage(Inspection inspection, Image inspectionImage) {
        this.inspectionImage = inspectionImage;
        if (inspection != null) {
            updateInspectionImage(inspection);
        }
    }
}
