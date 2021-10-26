package com.dragonappear.inha.domain.buying.value;

public enum BuyingStatus {

    // 입찰중
    구매입찰중   // 입찰중

    /// 거래 중
    , 거래중   // 결제완료
    , 구매완료 // 검수합격

    /// 거래 종료
    , 구매취소    // 미기입,미발송,검수탈락
    , 구매입찰종료   // 입찰종료
    , 배송완료 // 배송완료
}
