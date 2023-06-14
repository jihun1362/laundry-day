package com.meta.laundry_day.event_details.dto;

import lombok.Getter;

@Getter
public class EventRequestDto {
    private String title;
    private String content;
    private Long accumulatePoint;
    private double discountRate;
    private String from;
    private String until;
}
