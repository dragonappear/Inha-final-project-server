package com.dragonappear.inha.web.admin.deal;

import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.web.admin.deal.dto.DealWebDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class DealWebController {
    private final DealService dealService;

    @GetMapping("/admin/deals")
    public String getAllDeals(Model model) {
        List<DealWebDto> dtos = dealService.findAll().stream().map(deal -> {
            return DealWebDto.builder()
                    .dealId(deal.getId())
                    .createdDate(deal.getUpdatedDate())
                    .status(deal.getDealStatus())
                    .buyingId(deal.getBuying().getId())
                    .sellingId(deal.getSelling().getId())
                    .inspectionId((deal.getInspection() == null) ? null : deal.getInspection().getId())
                    .build();
        }).collect(Collectors.toList());
        model.addAttribute("deals", dtos);
        return "deal/dealList";
    }
}
