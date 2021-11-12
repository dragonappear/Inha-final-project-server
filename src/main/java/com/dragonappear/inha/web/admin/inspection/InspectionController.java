package com.dragonappear.inha.web.admin.inspection;

import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.InspectionImage;
import com.dragonappear.inha.domain.inspection.fail.FailInspection;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.inspection.InspectionImageService;
import com.dragonappear.inha.service.inspection.InspectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/web/inspections")
public class InspectionController {
    private final InspectionService inspectionService;
    private final InspectionImageService inspectionImageService;
    private final DealService dealService;
    private final FcmSendService fcmSendService;


    @GetMapping("/{dealId}")
    public String dealInspection(@PathVariable("dealId") Long dealId, Model model) {
        model.addAttribute("dealId", dealId);
        return "inspection/inspectionRegister";
    }

    @PostMapping("/{dealId}/register")
    public String inspectionRegister(@PathVariable("dealId") Long dealId, MultipartHttpServletRequest request) throws IOException {
        Deal deal = dealService.findById(dealId);
        if (deal.getInspection() != null) {
            Inspection inspection = deal.getInspection();
            inspectionService.delete(inspection.getId());
        }
        updateInspection(request, deal);

        String result;
        if (request.getParameter("result").equals("pass")) {
            result = "합격";
        } else {
            result = "탈락";
        }


        User seller = deal.getSelling().getSeller();
        User buyer = deal.getBuying().getPayment().getUser();
        String title = "검수 결과 알림";
        String body = deal.getSelling().getAuctionitem().getItem() + "는 검수에서 " + result + "하였습니다.\n"
                + "탈락한 거래는 마이페이지 거래내역에서 사유를 확인하실 수 있습니다.";
        try {
            fcmSendService.sendFCM(buyer, title, body);
            fcmSendService.sendFCM(seller, title, body);
        } catch (IOException e) {
            log.error("dealId:{} 검수결과 FCM 메시지가 전송되지 않았습니다.",deal.getId());
        }
        return "redirect:/web/deals";
    }

    private void updateInspection(MultipartHttpServletRequest request, Deal deal) throws IOException {
        List<MultipartFile> files = request.getFiles("file");
        String result = request.getParameter("result");
        Inspection inspection = getInspection(deal, result);
        inspectionService.save(inspection);
        updateInspectionImages(files,inspection);
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
                //String fileUrl = "/Users/dragonappear/Documents/study/inha_document/컴퓨터종합설계/code/inha/src/main/resources/static/inspections/";
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
