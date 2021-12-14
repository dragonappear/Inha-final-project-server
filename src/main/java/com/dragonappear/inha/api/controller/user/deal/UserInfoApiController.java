package com.dragonappear.inha.api.controller.user.deal;

import com.dragonappear.inha.api.controller.user.deal.dto.AddressDto;
import com.dragonappear.inha.api.controller.user.deal.dto.CardDto;
import com.dragonappear.inha.api.controller.user.deal.dto.PointDto;
import com.dragonappear.inha.api.controller.user.mypage.dto.UserAccountApiDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.user.UserCardInfo;
import com.dragonappear.inha.domain.value.Card;
import com.dragonappear.inha.service.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.api.returndto.ResultDto.returnResults;

@Api(tags = {"구매/판매 전 유저 정보 조회 API"})
@RestController
@RequiredArgsConstructor
public class UserInfoApiController {
    private final UserService userService;
    private final UserPointService userPointService;
    private final UserAddressService userAddressService;
    private final UserAccountService userAccountService;
    private final UserCardInfoService userCardInfoService;

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

    @ApiOperation(value = "유저 카드 조회 API", notes = "유저 카드 조회")
    @GetMapping("/api/v1/payments/cards/{userId}")
    public ResultDto getUserCardInfo(@PathVariable("userId") Long userId) {
        try {
            List<UserCardInfo> all = userCardInfoService.findAll(userId);
            List<CardDto> dtos = all.stream().map(userCard -> {
                return CardDto.builder()
                        .cardId(userCard.getId())
                        .cardCompanyName(userCard.getUserCard().getCardCompanyName())
                        .cardNumber(userCard.getUserCard().getCardNumber())
                        .build();
            }).collect(Collectors.toList());
            return returnResults(dtos);
        } catch (Exception e) {
            return returnResults(null);
        }
    }

    @ApiOperation(value = "유저 카드 등록 API", notes = "유저 카드 등록")
    @PostMapping("/api/v1/payments/cards/{userId}")
    public MessageDto saveUserCardInfo(@PathVariable("userId") Long userId, @RequestBody CardDto dto) {
        User user = userService.findOneById(userId);
        Card card = dto.toEntity();
        UserCardInfo cardInfo = new UserCardInfo(card, user);
        userCardInfoService.save(cardInfo);
        return MessageDto.builder()
                .message(MessageDto.getMessage("isRegistered", true))
                .build();
    }
}

