package com.meta.laundry_day.event_details.service;

import com.meta.laundry_day.common.config.S3Uploader;
import com.meta.laundry_day.common.exception.CustomException;
import com.meta.laundry_day.event_details.dto.EventRequestDto;
import com.meta.laundry_day.event_details.dto.EventResponseDto;
import com.meta.laundry_day.event_details.entity.EventDetails;
import com.meta.laundry_day.event_details.mapper.EventMapper;
import com.meta.laundry_day.event_details.repository.EventDetailsRepository;
import com.meta.laundry_day.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.meta.laundry_day.common.message.ErrorCode.EVENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventMapper eventMapper;
    private final EventDetailsRepository eventDetailsRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void createEvent(User user, EventRequestDto eventRequestDto, MultipartFile image) throws IOException {
        String imageUri = null;
        if (!image.isEmpty()) {
            imageUri = s3Uploader.upload(image, "event-images");
        }

        EventDetails eventDetails = eventMapper.toEventDetails(eventRequestDto, user, imageUri);

        eventDetailsRepository.save(eventDetails);
    }

    @Transactional(readOnly = true)
    public List<EventResponseDto> eventList() {
        List<EventDetails> eventDetailsList = eventDetailsRepository.findAllByOrderByCreatedAtDesc();
        List<EventResponseDto> eventResponseDtoList = new ArrayList<>();
        for (EventDetails e : eventDetailsList) {
            eventResponseDtoList.add(eventMapper.toResponse(e));
        }
        return eventResponseDtoList;
    }

    @Transactional(readOnly = true)
    public EventResponseDto eventDetail(Long eventId) {
        EventDetails eventDetails = eventDetailsRepository.findById(eventId).orElseThrow(() -> new CustomException(EVENT_NOT_FOUND));
        return eventMapper.toResponse(eventDetails);
    }

    @Transactional
    public void eventUpdate(Long eventId, EventRequestDto eventRequestDto, MultipartFile image) throws IOException {
        EventDetails eventDetails = eventDetailsRepository.findById(eventId).orElseThrow(() -> new CustomException(EVENT_NOT_FOUND));

        String title = eventDetails.getTitle();
        String content = eventDetails.getContent();
        Long accumulatePoint = eventDetails.getAccumulatePoint();
        double discountRate = eventDetails.getDiscountRate();
        String from = eventDetails.getDateFrom();
        String until = eventDetails.getDateUntil();
        String imageUri = eventDetails.getImage();

        //개별 데이터 기본값으로 오면 변경 X
        if (!eventRequestDto.getTitle().equals("")) title = eventRequestDto.getTitle();
        if (!eventRequestDto.getContent().equals("")) content = eventRequestDto.getContent();
        if (eventRequestDto.getAccumulatePoint() != 999999) accumulatePoint = eventRequestDto.getAccumulatePoint();
        if (eventRequestDto.getDiscountRate() != 999999) discountRate = eventRequestDto.getDiscountRate();
        if (!eventRequestDto.getFrom().equals("")) from = eventRequestDto.getFrom();
        if (!eventRequestDto.getUntil().equals("")) until = eventRequestDto.getUntil();
        if (!image.isEmpty()) {
            imageUri = s3Uploader.upload(image, "event-images");
        }

        eventDetails.update(title, content, accumulatePoint, discountRate, from, until, imageUri);
    }
}
