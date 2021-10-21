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
        return MyPageUserInfoDto.builder()
                .nickname(find.getNickname())
                .username(find.getUsername())
                .userRole(find.getUserRole().getTitle())
                .userPoint(find.getUserPoint().getTotal().getAmount())
                .imageUrl(find.getUserImage().getImage().getFileOriName())
                .userLikeCount(find.getUserLikeItems().size())
                .build();
    }
}
