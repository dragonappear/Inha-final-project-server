package com.dragonappear.inha.web.admin.inspection;

import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.fail.FailInspection;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.inspection.InspectionImageService;
import com.dragonappear.inha.service.inspection.InspectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/web/inspections")
public class InspectionController {
    private final InspectionService inspectionService;
    private final InspectionImageService inspectionImageService;
    private final DealService dealService;


    @GetMapping("/{dealId}")
    public String dealInspection(@PathVariable("dealId") Long dealId, Model model) {
        model.addAttribute("dealId", dealId);
        return "inspection/inspectionRegister";
    }

    @PostMapping("/{dealId}/register")
    public String inspectionRegister(@PathVariable("dealId") Long dealId, MultipartHttpServletRequest request) {
        Deal deal = dealService.findById(dealId);
        List<MultipartFile> file = request.getFiles("file");
        String result = request.getParameter("result");
        if (result.equals("pass")) {
            Inspection inspection = new PassInspection(deal);
            inspectionService.save(inspection);
        }
        else{
            Inspection inspection = new FailInspection(deal);
            inspectionService.save(inspection);
        }
        return "redirect:/web/deals";
    }

    /*public ImageDto updateProfile(MultipartFile file)  {
        try {
            String sourceFileName = file.getOriginalFilename();
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
            String fileUrl = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users/";
            //String fileUrl = "/Users/dragonappear/Documents/study/inha_document/컴퓨터종합설계/code/inha/src/main/resources/static/inspections/";
            String destinationFileName = RandomStringUtils.randomAlphabetic(32) + "." + sourceFileNameExtension;
            File destinationFile = new File(fileUrl + destinationFileName);
            destinationFile.getParentFile().mkdirs();
            file.transferTo(destinationFile);
            Image image = new Image(destinationFileName, sourceFileName, fileUrl);
            return ImageDto.builder()
                    .map(getDto("isFileInserted", true, "uploadStatus", "AllSuccess"))
                    .content(image)
                    .build();
        } catch (Exception e) {
            return ImageDto.builder()
                    .map(getDto("isFileInserted", false, "uploadStatus", "FileIsNotUploaded"))
                    .content(null)
                    .build();
        }
    }*/
}
