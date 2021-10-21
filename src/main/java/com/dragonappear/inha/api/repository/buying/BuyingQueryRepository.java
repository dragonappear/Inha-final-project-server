package com.dragonappear.inha.api.repository.buying;

import com.dragonappear.inha.api.controller.user.mypage.dto.MyPageUserBuyingSimpleDto;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.QBuying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dragonappear.inha.domain.buying.QBuying.*;
import static com.dragonappear.inha.domain.buying.value.BuyingStatus.*;
import static com.dragonappear.inha.domain.payment.QPayment.payment;

@RequiredArgsConstructor
@Repository
public class BuyingQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public MyPageUserBuyingSimpleDto getMyPageUserBuyingSimpleDto(Long userId){
        List<Buying> results = jpaQueryFactory.selectFrom(buying)
                .join(buying.payment, payment).on(buying.payment.id.eq(payment.id))
                .where(payment.user.id.eq(userId))
                .fetch();

        MyPageUserBuyingSimpleDto dto = new MyPageUserBuyingSimpleDto();
        results.stream().forEach(r ->{
            if(r.getBuyingStatus()== 구매중){
                dto.countBidding();
            }
            else if(r.getBuyingStatus()== 구매완료){
                dto.countOngoing();
            }
            else{
                dto.countEnd();
            }
            dto.countTotal();
        });
        return dto;
    }
}
