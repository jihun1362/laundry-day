package com.meta.laundry_day.event_details.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventResponseDto {
    private Long eventId;
    private String title;
    private String content;
    private String imageUrl;
    private String from;
    private String until;
    private int status;
    private String createat;
}
