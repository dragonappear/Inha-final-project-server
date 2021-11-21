package com.dragonappear.inha.web.admin.deal;

import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.api.service.firebase.FirebaseCloudMessageService;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.repository.deal.DealRepository;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.user.UserTokenService;
import com.dragonappear.inha.web.admin.deal.dto.DealWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.deal.value.DealStatus.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/web/admin/deals")
public class DealWebController {
    private final DealRepository dealRepository;

    @GetMapping
    public String getAllDeals(Model model) {
        List<Deal> all = dealRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedDate"));
        List<DealWebDto> dtos = all.stream().map(deal -> {
            return DealWebDto.builder()
                    .dealId(deal.getId())
                    .createdDate(deal.getCreatedDate())
                    .updatedDate(deal.getUpdatedDate())
                    .status(deal.getDealStatus())
                    .itemId(deal.getSelling().getAuctionitem().getItem().getId())
                    .amount(deal.getSelling().getAuctionitem().getPrice().getAmount())
                    .buyingId(deal.getBuying().getId())
                    .sellingId(deal.getSelling().getId())
                    .buyerId(deal.getBuying().getPayment().getUser().getId())
                    .sellerId(deal.getSelling().getSeller().getId())
                    .inspectionId((deal.getInspection() == null) ? null : deal.getInspection().getId())
                    .build();
        }).collect(Collectors.toList());
        model.addAttribute("deals", dtos);
        return "deal/dealList";
    }
}
