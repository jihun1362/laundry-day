package com.meta.laundry_day.event_details.controller;

import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ResultCode;
import com.meta.laundry_day.event_details.dto.EventRequestDto;
import com.meta.laundry_day.event_details.dto.EventResponseDto;
import com.meta.laundry_day.event_details.service.EventService;
import com.meta.laundry_day.security.util.UserDetailsImpl;
import com.meta.laundry_day.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.meta.laundry_day.common.message.ResultCode.EVENT_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.EVENT_DETAIL_REQUEST_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.EVENT_LIST_REQUEST_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.EVENT_MODIFY_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> createEvent(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                               @RequestPart EventRequestDto eventRequestDto,
                                                               @RequestPart MultipartFile image) throws IOException {
        eventService.createEvent(userDetails.getUser(), eventRequestDto, image);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(EVENT_CREATE_SUCCESS, null));
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<EventResponseDto>>> eventList() {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(EVENT_LIST_REQUEST_SUCCESS, eventService.eventList()));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ResponseDto<EventResponseDto>> eventDetail(@PathVariable Long eventId) {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(EVENT_DETAIL_REQUEST_SUCCESS, eventService.eventDetail(eventId)));
    }

    @PutMapping(value = "/{eventId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> createEvent(@RequestPart EventRequestDto eventRequestDto,
                                                               @RequestPart MultipartFile image,
                                                               @PathVariable Long eventId) throws IOException {
        eventService.eventUpdate(eventId, eventRequestDto, image);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(EVENT_MODIFY_SUCCESS, null));
    }
}
