package com.dragonappear.inha.api.repository.seliing.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MyPageUserSellingSimpleDto {
    private int total=0;
    private int bidding=0;
    private int ongoing=0;
    private int end=0;

    public void countTotal() {this.total++;}

    public void countBidding() {
        this.bidding++;
    }

    public void countOngoing() {this.ongoing++;}

    public void countEnd() {
        this.end++;
    }


    @Builder
    public MyPageUserSellingSimpleDto(int total, int bidding, int ongoing, int end) {
        this.total = total;
        this.bidding = bidding;
        this.ongoing = ongoing;
        this.end = end;
    }
}
