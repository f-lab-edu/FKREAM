package com.flab.fkream.deal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealHistoryCountDto {

    private DealStatus dealStatus;
    private int count;
}
