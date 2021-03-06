package com.dragonappear.inha.web.admin.inspection;

import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.InspectionImage;
import com.dragonappear.inha.domain.inspection.fail.FailInspection;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.repository.deal.DealRepository;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.inspection.InspectionImageService;
import com.dragonappear.inha.service.inspection.InspectionService;
import com.dragonappear.inha.web.admin.deal.dto.DealWebDto;
import com.dragonappear.inha.web.admin.inspection.dto.InspectionWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.deal.value.DealStatus.*;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/web/admin/inspections")
public class InspectionWebController {
    private final InspectionService inspectionService;
    private final InspectionImageService inspectionImageService;
    private final DealService dealService;
    private final FcmSendService fcmSendService;
    private final DealRepository dealRepository;


    @GetMapping
    public String getAllInspections(Model model) {
        List<Deal> all = dealRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedDate"));
        List<InspectionWebDto> dtos = all.stream().map(deal -> {
            return InspectionWebDto.builder()
                    .dealId(deal.getId())
                    .createdDate(deal.getCreatedDate())
                    .updatedDate(deal.getUpdatedDate())
                    .status(deal.getDealStatus())
                    .inspectionId((deal.getInspection() == null) ? null : deal.getInspection().getId())
                    .build();
        }).collect(Collectors.toList());
        model.addAttribute("deals", dtos);
        return "inspection/inspectionList";
    }

    @GetMapping("/deals/{dealId}")
    public String registerPage(@PathVariable("dealId") Long dealId, Model model) {
        model.addAttribute("dealId", dealId);
        return "inspection/inspectionRegister";
    }

    /**
     * ????????????
     */

    @PostMapping("/deals/{dealId}/receivingRegister")
    public String receivingRegister(@PathVariable("dealId") Long dealId) {
        Deal deal = dealService.findById(dealId);
        if (deal.getDealStatus() == ?????????????????????) {
            dealService.updateDealStatus(deal,????????????);
            String title = "????????? ?????? ??????";
            String body = deal.getSelling().getAuctionitem().getItem().getItemName() + " ????????? ?????????????????????.";
            User buyer = deal.getBuying().getPayment().getUser();
            User seller = deal.getSelling().getSeller();
            try {
                fcmSendService.sendFCM(buyer, title, body);
                fcmSendService.sendFCM(seller, title, body);
            } catch (Exception e) {
                log.error("dealId:{} ???????????? FCM ???????????? ???????????? ???????????????.",deal.getId());
            }
        }
        return "redirect:/web/admin/inspections";
    }

    /**
     * ???????????? ??????
     */

    @PostMapping("/deals/{dealId}/inspectionStart")
    public String inspectionStart(@PathVariable("dealId") Long dealId) {
        Deal deal = dealService.findById(dealId);
        if (deal.getDealStatus() == ????????????) {
            dealService.updateDealStatus(deal,????????????);
            String title = "????????? ???????????? ??????";
            String body = deal.getSelling().getAuctionitem().getItem().getItemName() + " ??? ????????? ???????????? ??????????????????.";
            User buyer = deal.getBuying().getPayment().getUser();
            User seller = deal.getSelling().getSeller();
            try {
                fcmSendService.sendFCM(buyer, title, body);
                fcmSendService.sendFCM(seller, title, body);
            } catch (Exception e) {
                log.error("dealId:{} ???????????? FCM ???????????? ???????????? ???????????????.",deal.getId());
            }
        }
        return "redirect:/web/admin/inspections";
    }

    /**
     * ????????????
     */

    @PostMapping("/deals/{dealId}/register")
    public String inspectionRegister(@PathVariable("dealId") Long dealId, MultipartHttpServletRequest request) throws IOException {
        Deal deal = dealService.findById(dealId);
        if (deal.getDealStatus() == ???????????? || deal.getDealStatus() == ???????????? || deal.getDealStatus() == ????????????) {
            if (deal.getInspection() != null) {
                Inspection inspection = deal.getInspection();
                inspectionService.delete(inspection.getId());
            }
            Long inspectionId = updateInspection(request, deal);

            String result;
            if (request.getParameter("result").equals("pass")) {
                result = "??????";
            } else {
                result = "??????";
            }

            User seller = deal.getSelling().getSeller();
            User buyer = deal.getBuying().getPayment().getUser();
            String title = "?????? ?????? ??????";
            String body = deal.getSelling().getAuctionitem().getItem().getItemName() + "??? ???????????? " + result + "???????????????.\n"
                    + "????????? ????????? ??????????????? ?????????????????? ????????? ???????????? ??? ????????????.";
            try {
                fcmSendService.sendFCM(buyer, title, body);
                fcmSendService.sendFCM(seller, title, body);
            } catch (Exception e) {
                log.error("dealId:{} ???????????? FCM ???????????? ???????????? ???????????????.",deal.getId());
            }
        }
        return "redirect:/web/admin/inspections";
    }

    /**
     * ??????????????????
     */

    private Long updateInspection(MultipartHttpServletRequest request, Deal deal) throws IOException {
        List<MultipartFile> files = request.getFiles("file");
        String result = request.getParameter("result");
        Inspection inspection = getInspection(deal, result);
        Long id = inspectionService.save(inspection);
        updateInspectionImages(files,inspection);
        return id;
    }

    private Inspection getInspection(Deal deal, String result) {
        Inspection inspection;
        if(result.equals("pass")) {
            inspection = new PassInspection(deal);
        }
        else{
            inspection = new FailInspection(deal);
        }
        return inspection;
    }

    public void updateInspectionImages(List<MultipartFile> files,Inspection inspection) throws IOException {
        List<InspectionImage> images = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String sourceFileName = file.getOriginalFilename();
                String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
                String fileUrl = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/inspections/";
                //String fileUrl = "/Users/dragonappear/Documents/study/programming/final_project/code/inha/src/main/resources/static/inspections/";
                String destinationFileName = RandomStringUtils.randomAlphabetic(32) + "." + sourceFileNameExtension;
                File destinationFile = new File(fileUrl + destinationFileName);
                destinationFile.getParentFile().mkdirs();
                file.transferTo(destinationFile);
                Image image = new Image(destinationFileName, sourceFileName, fileUrl);
                images.add(new InspectionImage(inspection, image));
            }
        } catch (Exception e) {
            throw e;
        }
        inspectionImageService.save(images);
    }
}
