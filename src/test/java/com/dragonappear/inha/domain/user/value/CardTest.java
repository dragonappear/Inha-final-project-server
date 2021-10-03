package com.dragonappear.inha.domain.user.value;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardTest {

    @Test
    public void 임베디드카드_테스트() throws Exception{
        Card card1 = new Card(CardCompanyName.신한카드, "1234-5678");
        Card card2 = new Card(CardCompanyName.신한카드, "1234-5678");

        Assertions.assertThat(card1).isEqualTo(card2);
    }
}