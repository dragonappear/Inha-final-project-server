package com.dragonappear.inha.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id")
    private Long id;

    @NotNull
    private Long total;
    @NotNull
    private Long used;
    @NotNull
    private Long earned;

    @OneToOne(fetch = LAZY, mappedBy = "userPoint")
    private User user;

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
