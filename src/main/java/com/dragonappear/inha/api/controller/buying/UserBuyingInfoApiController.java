package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"구매전 유저 정보 조회 API"})
@RestController
@RequiredArgsConstructor
public class UserBuyingInfoApiController {
    private final UserPointService userPointService;
    private final UserService userService;
    private final UserAddressService userAddressService;

    @ApiOperation(value = "유저 포인트 조회 API", notes = "유저 포인트 조회")
    @GetMapping("/payments/points/{userId}")
    public PointDto getUserPoint(@PathVariable("userId") Long userId) {
        return PointDto.builder()
                .total(userPointService.getTotal(userId).getAmount())
                .inspectionFee("무료")
                .deliveryFee("무료")
                .build();
    }

    @ApiOperation(value = "유저 주소조회 API by 유저아이디", notes = "유저 주소 조회")
    @GetMapping("/payments/addresses/find/{userId}")
    public AddressDto getUserAddressInfoByUserId(@PathVariable("userId") Long userId) {
        try {
            UserAddress userAddress = userAddressService.findByUserId(userService.findOneById(userId).getId())
                    .get(0);
            return AddressDto.builder()
                    .id(userAddress.getId())
                    .address(userAddress.getUserAddress())
                    .build();
        } catch (Exception e) {
            return AddressDto.builder()
                    .id(null)
                    .address(null)
                    .build();
        }
    }

    @ApiOperation(value = "유저 주소조회 API by 유저주소아이디", notes = "유저 주소 조회")
    @GetMapping("/payments/addresses/{userAddressId}")
    public AddressDto getUserAddressInfoByAddressId(@PathVariable("userAddressId") Long userAddressId) {
        try {
            UserAddress userAddress = userAddressService.findByUserAddressId(userAddressId);
            return AddressDto.builder()
                    .id(userAddress.getId())
                    .address(userAddress.getUserAddress())
                    .build();
        } catch (Exception e) {
            return AddressDto.builder()
                    .id(null)
                    .address(null)
                    .build();
        }
    }

    @ApiOperation(value = "유저 주소모두조회 API", notes = "유저 주소모두 조회")
    @GetMapping("/payments/addresses/all/{userId}")
    public List<AddressDto> getUserAllAddressInfo(@PathVariable("userId") Long userId) {
        try {
            return userAddressService.findByUserId(userService.findOneById(userId).getId()).stream()
                    .map(userAddress -> {
                        return AddressDto.builder()
                                .id(userAddress.getId())
                                .address((userAddress.getUserAddress()))
                                .build();
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<AddressDto>();
        }
    }

    @NoArgsConstructor
    @Data
    static class PointDto {
        private BigDecimal total;
        private String inspectionFee;
        private String deliveryFee;

        @Builder
        public PointDto(BigDecimal total, String inspectionFee, String deliveryFee) {
            this.total = total;
            this.inspectionFee = inspectionFee;
            this.deliveryFee = deliveryFee;
        }
    }

    @NoArgsConstructor
    @Data
    static class AddressDto {
        private Long id;
        private Address address;

        @Builder
        public AddressDto(Long id,Address address) {
            this.id = id;
            this.address = address;
        }
    }
}

