package com.meta.laundry_day.alarm.mapper;

import com.meta.laundry_day.alarm.dto.AlarmResponseDto;
import com.meta.laundry_day.alarm.entity.Alarm;
import com.meta.laundry_day.alarm.entity.AlarmType;
import com.meta.laundry_day.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AlarmMapper {

    public AlarmResponseDto toResponse(Alarm a) {
        return AlarmResponseDto.builder()
                .alarmId(a.getId())
                .type(a.getType().getType())
                .content(a.getContent())
                .status(a.getStatus())
                .createdAt(String.valueOf(a.getCreatedAt()))
                .build();
    }

    public Alarm toAlarm(User user, AlarmType alarmType) {
        return Alarm.builder()
                .type(alarmType)
                .content(alarmType.getContent())
                .status(1)
                .user(user)
                .build();
    }
}
