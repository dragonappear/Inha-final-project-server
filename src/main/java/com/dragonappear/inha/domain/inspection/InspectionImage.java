package com.dragonappear.inha.domain.inspection;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class InspectionImage {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inspection_image_id")
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileOriName;

    @Column(nullable = false)
    private String fileUrl;

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

    public InspectionImage(String fileName, String fileOriName, String fileUrl, Inspection inspection) {
        this.fileName = fileName;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
        if (inspection != null) {
            updateInspectionImage(inspection);
        }
    }
}
