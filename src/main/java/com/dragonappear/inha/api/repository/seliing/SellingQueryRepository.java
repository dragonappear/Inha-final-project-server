package com.dragonappear.inha.api.repository.seliing;


import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.selling.QSelling.*;

@RequiredArgsConstructor
@Repository
public class SellingQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MyPageUserSellingSimpleDto getMyPageUserSellingSimpleDto(Long userId) {
        List<Selling> results = jpaQueryFactory.selectFrom(selling)
                .where(selling.seller.id.eq(userId))
                .fetch();
        MyPageUserSellingSimpleDto dto = new MyPageUserSellingSimpleDto();
        results.stream().forEach(result ->{
            if(result.getSellingStatus()== SellingStatus.판매입찰중){
                dto.countBidding();
            }
            else if(result.getSellingStatus()==SellingStatus.거래중){
                dto.countOngoing();
            } else{
                dto.countEnd();
            }
            dto.countTotal();
        });
        return dto;
    }
}
