package com.dragonappear.inha.domain.deal.value;

public enum DealStatus {

    거래진행, // 거래 생성 시
    검수진행, // 검수 생성 시
    검수합격, 검수탈락,
    검수완료, // 합격,탈락 검수 생성 시

    // 거래 종료
    거래취소,  // 송장번호 미입고, 미입고 , 검수탈락
    거래완료 //  배송완료+정산완료
}
