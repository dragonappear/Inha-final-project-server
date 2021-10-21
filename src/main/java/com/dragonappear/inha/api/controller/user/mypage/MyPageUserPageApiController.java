package com.dragonappear.inha.api.controller.user.mypage;

import com.dragonappear.inha.api.controller.user.mypage.dto.MyPageUserBuyingSimpleDto;
import com.dragonappear.inha.api.controller.user.mypage.dto.MyPageUserInfoDto;
import com.dragonappear.inha.api.controller.user.mypage.dto.MyPageUserSellingSimpleDto;
import com.dragonappear.inha.api.repository.buying.BuyingQueryRepository;
import com.dragonappear.inha.api.repository.seliing.SellingQueryRepository;
import com.dragonappear.inha.api.repository.user.UserQueryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@Api(tags = {"마이페이지 유저 정보 조회 API"})
@RequiredArgsConstructor
@RestController
public class MyPageUserPageApiController {
    private final BuyingQueryRepository buyingQueryRepository;
    private final SellingQueryRepository sellingQueryRepository;
    private final UserQueryRepository userQueryRepository;

    @ApiOperation(value = "유저 기본 정보 조회", notes = "유저이름,유저닉네임,등급,포인트,관심상품 개수")
    @GetMapping("/users/mypage/{userId}")
    public MyPageUserInfoDto myPageUserInfoDto(@PathVariable("userId") Long userId) {
        return userQueryRepository.myPageUserInfoDto(userId);
    }

    @ApiOperation(value = "유저 구매 개수 조회", notes = "전체, 입찰중, 진행중, 종료")
    @GetMapping("/users/mypage/{userId}/buying")
    public MyPageUserBuyingSimpleDto myPageUserBuyingSimpleDto(@PathVariable("userId") Long userId) {
        return buyingQueryRepository.getMyPageUserBuyingSimpleDto(userId);
    }

    @ApiOperation(value = "유저 판매 개수 조회", notes = "전체, 입찰중, 진행중, 종료")
    @GetMapping("/users/mypage/{userId}/selling")
    public MyPageUserSellingSimpleDto myPageUserSellingSimpleDto(@PathVariable("userId") Long userId) {
        return sellingQueryRepository.getMyPageUserSellingSimpleDto(userId);
    }
}
