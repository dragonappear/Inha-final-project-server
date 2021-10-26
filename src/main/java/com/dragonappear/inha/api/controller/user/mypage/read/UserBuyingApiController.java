package com.dragonappear.inha.api.controller.user.mypage.read;

import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.api.repository.buying.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.dragonappear.inha.api.returndto.ResultDto.returnResults;

@Api(tags = {"마이페이지 유저 구매내역 상세 조회 API"})
@RequiredArgsConstructor
@RestController
public class UserBuyingApiController {
    private final BuyingQueryRepository buyingQueryRepository;

    @ApiOperation(value = "유저 구매입찰 상세 내역 조회 API", notes = "유저 구매입찰 상세 내역")
    @GetMapping("/users/mypage/buying/bid/{userId}")
    public ResultDto getUserBidItems(@PathVariable("userId") Long userId) {
        return returnResults(buyingQueryRepository.getMyPageUserBuyingBidDto(userId));
    }

    @ApiOperation(value = "유저 구매진행중 상세 내역 조회 API", notes = "유저 구매진행중 상세 내역 조회")
    @GetMapping("/users/mypage/buying/ongoing/{userId}")
    public ResultDto getUserOngoingItems(@PathVariable("userId") Long userId) {
        return returnResults(buyingQueryRepository.getMyPageUserBuyingOngoingDto(userId));
    }

    @ApiOperation(value = "유저 구매완료 상세 내역 조회 API", notes = "유저 구매완료 상세 내역 조회")
    @GetMapping("/users/mypage/buying/end/{userId}")
    public ResultDto getUserEndItems(@PathVariable("userId") Long userId) {
        return returnResults(buyingQueryRepository.getMyPageUserBuyingEndDto(userId));
    }

}
