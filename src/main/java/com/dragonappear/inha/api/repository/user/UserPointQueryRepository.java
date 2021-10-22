package com.dragonappear.inha.api.repository.user;

import com.dragonappear.inha.api.repository.user.dto.MyPageUserPointDto;
import com.dragonappear.inha.domain.user.QUserPoint;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dragonappear.inha.domain.user.QUserPoint.*;

@RequiredArgsConstructor
@Repository
public class UserPointQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    /*public List<MyPageUserPointDto> getMyPageUserPointDto(Long userId) {
            jpaQueryFactory.select()
                    .from(userPoint)
                    .
    }*/
}
