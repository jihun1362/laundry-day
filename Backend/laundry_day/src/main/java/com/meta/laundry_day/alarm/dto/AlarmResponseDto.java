package com.meta.laundry_day.alarm.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmResponseDto {
    private Long alarmId;
    private String type;
    private String content;
    private int status;
    private String createdAt;
}
