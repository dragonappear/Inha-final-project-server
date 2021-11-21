package com.dragonappear.inha.web.admin.settlement;

import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.domain.inspection.pass.Settlement;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.deal.DealRepository;
import com.dragonappear.inha.service.inspection.InspectionService;
import com.dragonappear.inha.service.inspection.pass.SettlementService;
import com.dragonappear.inha.service.selling.SellingService;
import com.dragonappear.inha.web.admin.settlement.dto.DepositWebDto;
import com.dragonappear.inha.web.admin.settlement.dto.SettlementWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.selling.value.SellingStatus.*;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping(value = "/web/admin/settlements")
public class SettlementWebController {
    private final SettlementService settlementService;
    private final DealRepository dealRepository;
    private final InspectionService inspectionService;
    private final SellingService sellingService;

    @GetMapping
    public String getAllSettlements(Model model) {
        List<Deal> all = dealRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedDate"));
        List<SettlementWebDto> deals = all.stream().filter(deal -> deal.getInspection() != null && deal.getInspection() instanceof PassInspection)
                .map(deal -> {
                    Settlement settlement = settlementService.findByInspectionId(deal.getInspection().getId());
                    return SettlementWebDto.builder()
                            .dealId(deal.getId())
                            .inspectionId(deal.getInspection().getId())
                            .createdDate(deal.getCreatedDate())
                            .updatedDate(deal.getUpdatedDate())
                            .saleAmount(deal.getSelling().getAuctionitem().getPrice().getAmount())
                            .settlementAmount(deal.getSelling().getAuctionitem().getPrice().getAmount())
                            .sellerId(deal.getSelling().getSeller().getId())
                            .dealStatus(deal.getDealStatus())
                            .sellingStatus(deal.getSelling().getSellingStatus())
                            .bankName((settlement == null) ? null : settlement.getAccount().getBankName().toString())
                            .accountHolder((settlement == null) ? null : settlement.getAccount().getAccountHolder())
                            .accountNumber((settlement == null) ? null : settlement.getAccount().getAccountNumber())
                            .build();
                })
                .collect(Collectors.toList());
        model.addAttribute("deals", deals);
        return "settlement/settlementList";
    }

    @PostMapping("/inspections/{inspectionId}")
    public String settlementRegister(@PathVariable("inspectionId") Long inspectionId, Model model) {
        PassInspection inspection = (PassInspection) inspectionService.findById(inspectionId);
        model.addAttribute("inspectionId", inspectionId);
        model.addAttribute("settlementAmount", inspection.getDeal().getSelling().getAuctionitem().getPrice().getAmount());
        return "settlement/settlementRegister";
    }

    @PostMapping("/inspections/{inspectionId}/register")
    public String saveSettlementRegister(@PathVariable("inspectionId") Long inspectionId, DepositWebDto dto) {
        PassInspection inspection = (PassInspection) inspectionService.findById(inspectionId);
        Settlement settlement = Settlement.builder()
                .dealId(inspection.getDeal().getId())
                .sellerId(inspection.getDeal().getSelling().getSeller().getId())
                .settlementPrice(new Money(dto.getAmount()))
                .account(new Account(dto.getBankName(), dto.getAccountNumber(), dto.getAccountHolder()))
                .auctionitemId(inspection.getDeal().getSelling().getAuctionitem().getItem().getId())
                .passInspection(inspection)
                .auctionitemPrice(inspection.getDeal().getSelling().getAuctionitem().getPrice())
                .build();
        settlementService.save(settlement);
        sellingService.updateSellingStatus(inspection.getDeal().getSelling(),정산완료);
        return "redirect:/web/admin/settlements";
    }

}
