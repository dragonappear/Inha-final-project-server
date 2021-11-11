package com.dragonappear.inha.web.admin.inspection;

import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.inspection.InspectionImageService;
import com.dragonappear.inha.service.inspection.InspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/inspections")
public class InspectionController {
    private final InspectionService inspectionService;
    private final InspectionImageService inspectionImageService;
    private final DealService dealService;

    @GetMapping("/{dealId}")
    public String dealInspection(@PathVariable("dealId") Long dealId, Model model) {
        //Deal deal = dealService.findById(dealId);
        return "inspection/inspectionRegister.html";
    }


    @PostMapping("/{dealId}/register")
    public String inspectionRegister(@PathVariable("dealId") Long dealId, MultipartFile[] files) {
        if (files[0].getSize() > 1) {
            for (MultipartFile file : files) {

            }
        }

        return "redirect://{dealId}";
    }
}
