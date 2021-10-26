package com.dragonappear.inha.api.controller.user.mypage.read;

import com.dragonappear.inha.api.controller.user.mypage.read.dto.PointDto;
import com.dragonappear.inha.api.repository.user.UserPointQueryRepository;
import com.dragonappear.inha.service.user.UserPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.api.controller.user.mypage.read.dto.PointDto.getResults;

@Api(tags = {"마이페이지 유저 포인트 상세 조회 API"})
@RequiredArgsConstructor
@RestController
public class UserPointApiController {
    private final UserPointQueryRepository userPointQueryRepository;
    private final UserPointService userPointService;

    @ApiOperation(value = "마이페이지 유저포인트 내역 상세 조회 API", notes = "마이페이지 유저포인트 내역 상세 조회")
    @GetMapping("/users/mypage/points/{userId}")
    public PointDto getMyPageUserPointDtos(@PathVariable("userId") Long userId) {
        return getResults(userPointService.findLatestPoint(userId).getTotal().getAmount(),
                userPointQueryRepository.getMyPageUserPointDto(userId));
    }
}
