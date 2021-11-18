package com.dragonappear.inha.api.controller.user.deal;

import com.dragonappear.inha.api.controller.user.deal.dto.AddressDto;
import com.dragonappear.inha.api.controller.user.deal.dto.PointDto;
import com.dragonappear.inha.api.controller.user.mypage.dto.UserAccountApiDto;
import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.exception.user.NotFoundUserAccountException;
import com.dragonappear.inha.service.user.UserAccountService;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.service.user.UserPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.api.returndto.ResultDto.returnResults;

@Api(tags = {"구매/판매 전 유저 정보 조회 API"})
@RestController
@RequiredArgsConstructor
public class UserInfoApiController {
    private final UserPointService userPointService;
    private final UserAddressService userAddressService;
    private final UserAccountService userAccountService;

    @ApiOperation(value = "유저 포인트조회 API by 유저아이디", notes = "유저 포인트 조회")
    @GetMapping("/api/v1/payments/points/{userId}")
    public PointDto getUserPoint(@PathVariable("userId") Long userId)  {
        BigDecimal amount = userPointService.getTotal(userId).getAmount();
        return PointDto.builder()
                .total(amount)
                .inspectionFee("무료")
                .deliveryFee("무료")
                .build();
    }

    @ApiOperation(value = "유저 대표주소조회 API by 유저아이디", notes = "유저 주소 조회")
    @GetMapping("/api/v1/payments/addresses/find/{userId}")
    public AddressDto getUserAddressInfoByUserId(@PathVariable("userId") Long userId) {
        UserAddress userAddress = userAddressService.findByUserId(userId).get(0);
        return AddressDto.builder()
                .addressId(userAddress.getId())
                .address(userAddress.getUserAddress())
                .build();
    }

    @ApiOperation(value = "유저 주소조회 API by 주소아이디", notes = "유저 주소 조회")
    @GetMapping("/api/v1/payments/addresses/{userAddressId}")
    public AddressDto getUserAddressInfoByAddressId(@PathVariable("userAddressId") Long userAddressId) {
        try {
            UserAddress userAddress = userAddressService.findByUserAddressId(userAddressId);
            return AddressDto.builder()
                    .addressId(userAddress.getId())
                    .address(userAddress.getUserAddress())
                    .build();
        } catch (Exception e) {
            return AddressDto.builder()
                    .addressId(null)
                    .address(null)
                    .build();
        }
    }

    @ApiOperation(value = "유저 주소모두조회 API by 유저아이디로", notes = "유저 주소모두 조회")
    @GetMapping("/api/v1/payments/addresses/all/{userId}")
    public ResultDto getUserAllAddressInfo(@PathVariable("userId") Long userId) {
        try {
            List<AddressDto> dtos = userAddressService.findByUserId(userId).stream()
                    .map(userAddress -> {
                        return AddressDto.builder()
                                .addressId(userAddress.getId())
                                .address((userAddress.getUserAddress()))
                                .build();
                    }).collect(Collectors.toList());
            return returnResults(dtos);
        } catch (Exception e) {
            return returnResults(new ArrayList<AddressDto>());
        }
    }

    @ApiOperation(value = "유저 계좌조회 API by 유저아이디로", notes = "유저 계좌조회 조회")
    @GetMapping("/api/v1/payments/accounts/{userId}")
    public UserAccountApiDto getUserAccountInfo(@PathVariable("userId") Long userId){
        UserAccount account = userAccountService.findByUserId(userId);
        return UserAccountApiDto.builder()
                    .account(account)
                    .build();
    }

}

