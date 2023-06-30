package com.meta.laundry_day.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointResponseDto {
    private Long UserPointId;
    private String division;
    private Double point;
    private String createdAt;
}
