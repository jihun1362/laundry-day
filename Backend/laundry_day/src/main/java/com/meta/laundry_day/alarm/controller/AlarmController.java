package com.meta.laundry_day.alarm.controller;

import com.meta.laundry_day.alarm.dto.AlarmResponseDto;
import com.meta.laundry_day.alarm.service.AlarmService;
import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ResultCode;
import com.meta.laundry_day.security.util.UserDetailsImpl;
import com.meta.laundry_day.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.meta.laundry_day.common.message.ResultCode.ALARM_CHECK_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ALARM_LIST_REQUEST_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alram")
public class AlarmController {
    private final AlarmService alarmService;

    @PatchMapping("/{alarmId}")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<ResultCode>> checkAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable Long alarmId){
        alarmService.checkAlarm(userDetails.getUser(), alarmId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ALARM_CHECK_SUCCESS, null));
    }

    @GetMapping("")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<List<AlarmResponseDto>>> alarmList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ALARM_LIST_REQUEST_SUCCESS, alarmService.alarmList(userDetails.getUser())));
    }
}
