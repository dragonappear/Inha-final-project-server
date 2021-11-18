package com.dragonappear.inha.api.controller.user.mypage.read;


import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.api.repository.seliing.SellingQueryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.dragonappear.inha.api.returndto.ResultDto.returnResults;

@Api(tags = {"마이페이지 유저 판매내역 상세 조회 API"})
@RequiredArgsConstructor
@RestController
public class UserSellingApiController {
    private final SellingQueryRepository sellingQueryRepository;

    @ApiOperation(value = "유저 판매입찰 상세 내역 조회 API", notes = "유저 판매입찰 상세 내역 조회")
    @GetMapping("/api/v1/users/mypage/selling/bid/{userId}")
    public ResultDto getMyPageUserSellingBidDto(@PathVariable("userId") Long userId) {
        return returnResults(sellingQueryRepository.getMyPageUserSellingBidDto(userId));
    }

    @ApiOperation(value = "유저 판매진행 상세 내역 조회 API", notes = "유저 판매진행 상세 내역 조회")
    @GetMapping("/api/v1/users/mypage/selling/ongoing/{userId}")
    public ResultDto getMyPageUserSellingOngoingDto(@PathVariable("userId") Long userId) {
        return returnResults(sellingQueryRepository.getMyPageUserSellingOngoingDto(userId));
    }

    @ApiOperation(value = "유저 판매종료 상세 내역 조회 API", notes = "유저 판매종료 상세 내역 조회")
    @GetMapping("/api/v1/users/mypage/selling/end/{userId}")
    public ResultDto getMyPageUserSellingEndDto(@PathVariable("userId") Long userId) {
        return returnResults(sellingQueryRepository.getMyPageUserSellingEndDto(userId));
    }
}
