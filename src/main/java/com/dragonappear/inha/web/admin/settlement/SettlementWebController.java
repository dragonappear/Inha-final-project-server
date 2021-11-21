package com.dragonappear.inha.web.admin.settlement;

import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.inspection.pass.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping(value = "/web/admin/settlements")
public class SettlementWebController {
    private final SettlementService settlementService;
    private final DealService dealService;

    @GetMapping
    public String getAllSettlements(Model model) {
        return "settlement/settlementList";
    }

    @PostMapping("/register")
    public String settlementRegister() {
        return "redirect://home";
    }
}
