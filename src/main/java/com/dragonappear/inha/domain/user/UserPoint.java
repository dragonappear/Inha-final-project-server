package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.user.value.PointStatus;
import com.dragonappear.inha.domain.value.Money;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"})})
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

    @Enumerated(STRING)
    @Column(nullable = false)
    private PointStatus pointStatus;

    /**
     * 연관관계
     */

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 연관관계 메서드
     */

    private void updateUserPoint(User user) {
        this.user = user;
        user.getUserPoints().add(this);
    }

    /**
     * 생성자 메서드
     */

    public UserPoint(User user) {
        this.total = Money.wons(1000L);
        this.earned = Money.wons(1000L);
        this.used = Money.wons(0L);
        if (user != null) {
            updateUserPoint(user);
        }
        this.pointStatus=PointStatus.적립;
    }

    public UserPoint(User user, UserPoint userPoint,PointStatus pointStatus) {
        this.total = userPoint.getTotal();
        this.used = userPoint.getUsed();
        this.earned = userPoint.getEarned();
        if (user != null) {
            updateUserPoint(user);
        }
        this.pointStatus = pointStatus;
    }

    public UserPoint( Money total,Money used,Money earned) {
        this.total = total;
        this.used = used;
        this.earned = earned;
    }



    /**
     * 비즈니스 로직
     */
    public UserPoint plus(BigDecimal amount){
        Money money = new Money(amount);
        return new UserPoint(this.total.plus(money)
                    , this.used
                    , this.earned.plus(money));
    }

    public UserPoint minus(BigDecimal amount)  {
        Money money = new Money(amount);
        return new UserPoint(this.total.minus(money)
                    , this.used.plus(money)
                    , this.earned);
    }
}
