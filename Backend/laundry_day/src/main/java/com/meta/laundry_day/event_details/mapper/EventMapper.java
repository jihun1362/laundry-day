package com.meta.laundry_day.event_details.mapper;

import com.meta.laundry_day.event_details.dto.EventRequestDto;
import com.meta.laundry_day.event_details.dto.EventResponseDto;
import com.meta.laundry_day.event_details.entity.EventDetails;
import com.meta.laundry_day.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EventMapper {

    public EventResponseDto toResponse(EventDetails e) {
        return EventResponseDto.builder()
                .eventId(e.getId())
                .title(e.getTitle())
                .content(e.getContent())
                .imageUrl(e.getImage())
                .from(e.getDateFrom())
                .until(e.getDateUntil())
                .status(e.getStatus())
                .createat(String.valueOf(e.getCreatedAt()))
                .build();
    }

    public EventDetails toEventDetails(EventRequestDto eventRequestDto, User user, String imageUri) {
        int status = 0;
        LocalDateTime now = LocalDateTime.now();
        String str = eventRequestDto.getFrom();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from = LocalDateTime.parse(str, formatter);
        if (from.isBefore(now) || from.isEqual(now)) {
            status = 1;
        }
        return EventDetails.builder()
                .title(eventRequestDto.getTitle())
                .content(eventRequestDto.getContent())
                .image(imageUri)
                .accumulatePoint(eventRequestDto.getAccumulatePoint())
                .discountRate(eventRequestDto.getDiscountRate())
                .dateFrom(eventRequestDto.getFrom())
                .dateUntil(eventRequestDto.getUntil())
                .status(status)
                .user(user)
                .build();
    }
}
