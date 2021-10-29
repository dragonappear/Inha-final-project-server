package com.dragonappear.inha.api.service.selling;

import com.dragonappear.inha.api.controller.selling.dto.BidSellingDto;
import com.dragonappear.inha.api.controller.selling.dto.InstantSellingDto;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.exception.selling.SellingException;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.item.ItemService;
import com.dragonappear.inha.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.dragonappear.inha.domain.buying.value.BuyingStatus.구매입찰중;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ValidateSellingService {
    private final UserService userService;
    private final ItemService itemService;
    private final BuyingService buyingService;

    public void validateBidSelling(BidSellingDto dto) {
        try {
            validateEndDate(dto.getEndDate());
            validatePrice(dto.getPrice());
            validateUser(dto.getUserId());
            validateItem(dto.getItemId());
        } catch (Exception e) {
            throw new SellingException(e.getMessage());
        }
    }

    public void validateInstantSelling(InstantSellingDto dto) {
        try {
            validateBuying(dto.getBuyingId());
            validatePrice(dto.getPrice());
            validateUser(dto.getUserId());
            validateItem(dto.getItemId());
        } catch (Exception e) {
            throw new SellingException(e.getMessage());
        }
    }

    /**
     *
     * 검증 로직
     *
     */

    private void validateBuying(Long buyingId) {
        Buying buying = buyingService.findById(buyingId);
        if (buying.getBuyingStatus() != 구매입찰중) {
            throw new IllegalArgumentException("이미 거래진행 중인 구매아이디입니다.");
        }
    }

    private void validatePrice(BigDecimal price) {
        if ( new Money(BigDecimal.valueOf(0L)).isGreaterThanOrEqual(new Money(price))  ) {
            throw new IllegalArgumentException("상품 가격 입력이 잘못되었습니다.");
        }
    }

    private void validateItem(Long itemId) {
        itemService.findByItemId(itemId);
    }

    private void validateUser(Long userId) {
        userService.findOneById(userId);
    }

    private void validateEndDate(LocalDateTime endDate) {
        if (endDate.isBefore(LocalDateTime.now().plusMinutes(1))) {
            throw new IllegalArgumentException("입찰만료시간이 올바르지 않습니다.");
        }
    }
}
