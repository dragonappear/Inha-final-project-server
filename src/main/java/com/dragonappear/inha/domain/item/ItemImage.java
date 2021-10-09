package com.dragonappear.inha.domain.item;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ItemImage extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long id;


    @Embedded
    private Image itemImage;

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
    public ItemImage( Item item,Image image) {
        this.itemImage = image;
        if (item != null) {
            updateItemImage(item);
        }
    }

}