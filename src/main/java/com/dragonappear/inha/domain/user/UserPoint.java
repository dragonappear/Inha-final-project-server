package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.JpaBaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserPoint extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id")
    private Long id;

    @Column(nullable = false)
    private Long total;
    @Column(nullable = false)
    private Long used;
    @Column(nullable = false)
    private Long earned;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY, mappedBy = "userPoint")
    private User user;

    /**
     * 생성자 메서드
     */

    public UserPoint(Long total, Long used, Long earned) {
        this.total = total;
        this.used = used;
        this.earned = earned;
    }

    /**
     * 연관관계 메서드
     */

    public void updateUser(User user) {
        this.user = user;
    }


    /**
     * 비즈니스 로직
     */
    public void plus(Long amount) {
        if(amount<=0){
            new IllegalArgumentException("포인트 적립 파라미터 오류");
        }
        else{
            total += amount;
            earned += amount;
        }
    }

    public void minus(Long amount) {
        if(amount>=0){
            new IllegalArgumentException("포인트 차감 파라미터 오류");
        }
        else{
            total -= amount;
            used -= amount;
        }
    }


}
