package com.dragonappear.inha.api.repository.user;


import com.dragonappear.inha.api.controller.user.mypage.dto.MyPageUserInfoDto;
import com.dragonappear.inha.domain.user.QUser;
import com.dragonappear.inha.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.dragonappear.inha.domain.user.QUser.*;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MyPageUserInfoDto myPageUserInfoDto(Long userId) {
        User find = jpaQueryFactory.selectFrom(user)
                .where(user.id.eq(userId))
                .fetchOne();
        if (find == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return MyPageUserInfoDto.builder()
                .nickname(find.getNickname())
                .username(find.getUsername())
                .userRole("USER")
                .userPoint(find.getUserPoints().get(find.getUserPoints().size()-1).getTotal().getAmount())
                .imageUrl(find.getUserImage().getImage().getFileName())
                .userLikeCount(find.getUserLikeItems().size())
                .build();
    }
}
