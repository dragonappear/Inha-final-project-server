package com.dragonappear.inha.domain.value;

import lombok.Getter;

@Getter
public enum CourierName {
    CJ대한통운("04")
    ,한진택배("05")
    ,롯데택배("08")
    ,우체국택배("01")
    ,로젠택배("06")
    ,일양로지스("11")
    ,한덱스("20")
    ,대신택배("22")
    ,경동택배("23")
    ,합동택배("32")
    , CU편의점택배("46")
    , GSPostbox택배("24");

    private String code;

    CourierName(String code) {
        this.code = code;
    }
}
