package com.dragonappear.inha.domain.value;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DeliveryTest {

    @Test
    public void 배송_테스트() throws Exception{
        //given
        Delivery delivery1 = new Delivery(CourierName.CJ대한통운, "1234-1234");
        Delivery delivery2 = new Delivery(CourierName.CJ대한통운, "1234-1234");
        Delivery delivery3 = new Delivery(CourierName.CJ대한통운, "1234-5234");
        //when

        //then
        Assertions.assertThat(delivery1).isEqualTo(delivery2);
        Assertions.assertThat(delivery2).isNotEqualTo(delivery3);
    }
}