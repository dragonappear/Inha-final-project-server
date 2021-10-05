package com.dragonappear.inha.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
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
    @JoinColumn(name = "item_id")
    private Item item;

    /**
     * 연관관계편의메서드
     */

    private void updateItemImage(Item item) {
        this.item = item;
        item.getItemImages().add(this);
    }

    /**
     * 생성자메서드
     */
    public ItemImage( Item item,String fileName, String fileOriName, String fileUrl) {
        this.fileName = fileName;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
        if (item != null) {
            updateItemImage(item);
        }
    }

}