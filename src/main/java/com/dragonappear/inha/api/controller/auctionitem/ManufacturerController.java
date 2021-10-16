package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerRepository manufacturerRepository;
    @GetMapping("/api/v2/manufacturers")
    public List<ManufacturerName> manufacturerNames() {
        return manufacturerRepository.findAll()
                .stream()
                .map(manufacturer -> manufacturer.getManufacturerName())
                .collect(Collectors.toList());
    }
}
