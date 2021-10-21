package com.dragonappear.inha.domain.auctionitem.value;

public enum AuctionitemStatus {
    경매중, // 경매품 생성할때
    거래중, // 판매 거래중상태로 변할때


    // 종료
    경매완료, // 판매 완료상태로 변할때
    경매기한만료, // 판매 판매입찰종료상태로 변할때
    경매취소 // 판매 판매취소상태로 변할때

}
