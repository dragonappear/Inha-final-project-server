package com.dragonappear.inha.domain.selling.value;

public enum SellingStatus {
    판매입찰중   // 판매입찰중
    , 판매입찰종료 // 입찰판매기한종료
    , 거래중   // 1:1 거래 성사
    , 판매완료 // 검수합격
    ,판매취소 // 검수탈락
    ,정산완료 // 최종판매상태
}
