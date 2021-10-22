package com.dragonappear.inha.domain.item;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id","user_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserLikeItem extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_item_id")
    private Long id;

    /**
     * 연관관계
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * 연관관계편의메서드
     */

    private void updateUser(User user) {
        this.user = user;
        user.getUserLikeItems().add(this);
    }

    private void updateItem(Item item) {
        this.item = item;
        item.getUserLikeItems().add(this);
        item.like();
    }

    /**
     * 생성자메서드
     */
    public UserLikeItem(Item item, User user) {
        if(user!=null){
            updateUser(user);
        }
        if(item!=null){
            updateItem(item);
        }
    }
}