package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserPoint extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id")
    private Long id;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "total"))})
    @Embedded
    private Money total;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "used"))})
    @Embedded
    private Money used;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "earned"))})
    @Embedded
    private Money earned;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 연관관계 메서드
     */

    private void updateUserPoint(User user) {
        this.user = user;
        user.updateUserPoint(this);
    }

    /**
     * 생성자 메서드
     */

    public UserPoint(User user) {
        this.total = Money.wons(0L);
        this.earned = Money.wons(0L);
        this.used = Money.wons(0L);
        if (user != null) {
            updateUserPoint(user);
        }
    }

    /**
     * 비즈니스 로직
     */
    public void plus(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO)<0){
            new IllegalArgumentException("포인트적립 파라미터 오류");
        }
        else{
            Money money = new Money(amount);
            this.earned.plus(money);
            this.total.plus(money);
        }
    }

    public void minus(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO)<0){
            new IllegalArgumentException("포인트차감 파라미터 오류");
        }
        else{
            Money money = new Money(amount);
            this.used.plus(money);
            this.total.minus(money);
        }
    }
}
