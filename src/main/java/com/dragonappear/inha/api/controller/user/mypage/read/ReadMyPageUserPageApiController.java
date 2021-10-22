package com.dragonappear.inha.api.controller.user.mypage.read;

import com.dragonappear.inha.api.controller.user.mypage.dto.*;
import com.dragonappear.inha.api.repository.buying.BuyingQueryRepository;
import com.dragonappear.inha.api.repository.buying.dto.MyPageUserBuyingSimpleDto;
import com.dragonappear.inha.api.repository.seliing.dto.MyPageUserSellingSimpleDto;
import com.dragonappear.inha.api.repository.seliing.SellingQueryRepository;
import com.dragonappear.inha.api.repository.user.UserQueryRepository;
import com.dragonappear.inha.service.user.UserAccountService;
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
import java.util.stream.Collectors;


@Api(tags = {"마이페이지 유저 정보 조회 API"})
@RequiredArgsConstructor
@RestController
public class ReadMyPageUserPageApiController {
    private final BuyingQueryRepository buyingQueryRepository;
    private final SellingQueryRepository sellingQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final UserService userService;
    private final UserAddressService userAddressService;
    private final UserAccountService userAccountService;

    @ApiOperation(value = "유저 기본 정보 조회", notes = "유저이름,유저닉네임,등급,포인트,관심상품 개수")
    @GetMapping("/users/mypage/{userId}")
    public MyPageUserInfoDto myPageUserInfoDto(@PathVariable("userId") Long userId) {
        return userQueryRepository.myPageUserInfoDto(userId);
    }

    @ApiOperation(value = "유저 구매 개수 조회", notes = "전체, 입찰중, 진행중, 종료")
    @GetMapping("/users/mypage/buying/{userId}")
    public MyPageUserBuyingSimpleDto myPageUserBuyingSimpleDto(@PathVariable("userId") Long userId) {
        return buyingQueryRepository.getMyPageUserBuyingSimpleDto(userId);
    }

    @ApiOperation(value = "유저 판매 개수 조회", notes = "전체, 입찰중, 진행중, 종료")
    @GetMapping("/users/mypage/selling/{userId}")
    public MyPageUserSellingSimpleDto myPageUserSellingSimpleDto(@PathVariable("userId") Long userId) {
        return sellingQueryRepository.getMyPageUserSellingSimpleDto(userId);
    }

    @ApiOperation(value = "유저 로그인 정보 조회", notes = "이메일, 휴대폰 번호")
    @GetMapping("/users/mypage/loginInfo/{userId}")
    public LoginInfoApiDto loginInfoApiDto(@PathVariable("userId") Long userId) {
        return new LoginInfoApiDto(userService.findOneById(userId));
    }

    @ApiOperation(value = "유저 주소 조회", notes = "우편번호,시,도로명,상세주소")
    @GetMapping("/users/mypage/addresses/{userId}")
    public Results userAddressApiDto(@PathVariable("userId") Long userId) {
        List<UserAddressApiDto> dtos = userAddressService.findByUserId(userId).stream()
                .map(userAddress -> UserAddressApiDto.builder()
                        .addressId(userAddress.getId())
                        .address(userAddress.getUserAddress())
                        .build())
                .collect(Collectors.toList());

        return Results.builder()
                .count(dtos.size())
                .items(dtos.stream().map(dto-> (Object) dto).collect(Collectors.toList()))
                .build();
    }

    @ApiOperation(value = "유저 계좌 조회", notes = "은행,계좌번호,예금주")
    @GetMapping("/users/mypage/accounts/{userId}")
    public UserAccountApiDto userAccountDto(@PathVariable("userId") Long userId) {
        return UserAccountApiDto.builder()
                .account(userAccountService.findByUserId(userId))
                .build();
    }

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
