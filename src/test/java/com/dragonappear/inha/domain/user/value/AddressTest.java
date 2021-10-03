package com.dragonappear.inha.domain.user.value;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressTest {

    @Test
    public void 임베디드주소_테스트() throws Exception{
        Address address1 = new Address("incheon", "inharo", "127", "1234");
        Address address2 = new Address("incheon", "inharo", "127", "1234");

        Assertions.assertThat(address1).isEqualTo(address2);
    }
}