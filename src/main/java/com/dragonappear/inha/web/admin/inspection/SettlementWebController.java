package com.dragonappear.inha.web.admin.inspection;

import com.dragonappear.inha.service.inspection.pass.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Slf4j
@Controller(value = "/web/admin/settlements")
public class SettlementWebController {
    private SettlementService settlementService;
}
