package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ManufacturerRepositoryTest {
    @Autowired ManufacturerRepository manufacturerRepository;
    @Test
    public void 제조사생성_테스트() throws Exception{
        //given
        Manufacturer newManufacturer = new Manufacturer(ManufacturerName.삼성);
        manufacturerRepository.save(newManufacturer);
        //when
        Manufacturer findManufacturer = manufacturerRepository.findById(newManufacturer.getId()).get();
        //then
        assertThat(findManufacturer).isEqualTo(newManufacturer);
        assertThat(findManufacturer.getId()).isEqualTo(newManufacturer.getId());
        assertThat(findManufacturer.getManufacturerName()).isEqualTo(newManufacturer.getManufacturerName());
    }
}