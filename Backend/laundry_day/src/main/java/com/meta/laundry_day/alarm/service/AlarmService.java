package com.meta.laundry_day.alarm.service;

import com.meta.laundry_day.alarm.dto.AlarmResponseDto;
import com.meta.laundry_day.alarm.entity.Alarm;
import com.meta.laundry_day.alarm.mapper.AlarmMapper;
import com.meta.laundry_day.alarm.repository.AlarmRepository;
import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.meta.laundry_day.common.message.ErrorCode.ALARM_NOT_FOUND;
import static com.meta.laundry_day.common.message.ErrorCode.AUTHORIZATION_UPDATE_FAIL;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmMapper alarmMapper;

    @Transactional
    public void checkAlarm(User user, Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new CustomException(ALARM_NOT_FOUND));

        //권한체크
        if (!alarm.getUser().getId().equals(user.getId())) {
            throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
        }

        alarm.check();
    }

    @Transactional
    public List<AlarmResponseDto> alarmList(User user) {
        List<Alarm> alarms = alarmRepository.findAllByUserOrderByCreatedAtDesc(user);
        List<AlarmResponseDto> alarmResponseDtoList = new ArrayList<>();
        for (Alarm a : alarms) {
            alarmResponseDtoList.add(alarmMapper.toResponse(a));
        }
        return alarmResponseDtoList;
    }
}
