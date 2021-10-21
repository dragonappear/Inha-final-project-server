package com.dragonappear.inha.api.controller.user.mypage;

import com.dragonappear.inha.api.controller.user.mypage.dto.*;
import com.dragonappear.inha.api.repository.buying.BuyingQueryRepository;
import com.dragonappear.inha.api.repository.seliing.SellingQueryRepository;
import com.dragonappear.inha.api.repository.user.UserQueryRepository;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = {"마이페이지 유저 정보 조회 API"})
@RequiredArgsConstructor
@RestController
public class MyPageUserPageApiController {
    private final BuyingQueryRepository buyingQueryRepository;
    private final SellingQueryRepository sellingQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final UserService userService;
    private final UserAddressService userAddressService;

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

    @ApiOperation(value = "유저 로그인 정보 조회", notes = "이메일, 휴대폰 번호")
    @GetMapping("/users/mypage/{userId}/loginInfo")
    public LoginInfoApiDto loginInfoApiDto(@PathVariable("userId") Long userId) {
        return new LoginInfoApiDto(userService.findOneById(userId));
    }

    /*@ApiOperation(value = "유저 주소 조회", notes = "우편번호,시,도로명,상세주소")
    @GetMapping("/users/mypage/{userId}/addresses")
    public UserAddressApiDto userAddressApiDto(@PathVariable("userId") Long userId) {

    }*/

    /**
     * DTO
     */

    @Data
    static class Results<T> {
        private int count;
        private List<T> items;

        @Builder
        public Results(int count, List<T> items) {
            this.count = count;
            this.items = items;
        }
    }
}
