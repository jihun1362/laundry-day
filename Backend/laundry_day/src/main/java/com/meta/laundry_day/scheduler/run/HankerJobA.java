package com.meta.laundry_day.scheduler.run;


import com.meta.laundry_day.event_details.entity.EventDetails;
import com.meta.laundry_day.event_details.repository.EventDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
@RequiredArgsConstructor
public class HankerJobA extends QuartzJobBean {

    private final EventDetailsRepository eventDetailsRepository;

    private static final Logger log = LoggerFactory.getLogger(HankerJobA.class);

    @Override
    @Transactional(readOnly = true)
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("매일 자정 12시 마다 실행 될 작업 작성 공간");

        List<EventDetails> eventDetails = eventDetailsRepository.findAll();

        for (EventDetails e : eventDetails) {
            int status = 0;
            LocalDateTime now = LocalDateTime.now();
            String dateFrom = e.getDateFrom();
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime from = LocalDateTime.parse(dateFrom, formatter1);
            if (from.isBefore(now)) {
                status = 1;
            }

            String dateUntil = e.getDateUntil();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime until = LocalDateTime.parse(dateUntil, formatter2);
            if (!until.isAfter(now)){
                status = 0;
            }
        }

    }
}

