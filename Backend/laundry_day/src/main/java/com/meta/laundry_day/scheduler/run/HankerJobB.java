package com.meta.laundry_day.scheduler.run;

import com.meta.laundry_day.alarm.entity.Alarm;
import com.meta.laundry_day.alarm.entity.AlarmType;
import com.meta.laundry_day.alarm.mapper.AlarmMapper;
import com.meta.laundry_day.alarm.repository.AlarmRepository;
import com.meta.laundry_day.order.entity.Order;
import com.meta.laundry_day.order.entity.Progress;
import com.meta.laundry_day.order.entity.ProgressStatus;
import com.meta.laundry_day.order.repository.OrderRepository;
import com.meta.laundry_day.order.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HankerJobB extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(HankerJobA.class);

    private final OrderRepository orderRepository;
    private final ProgressRepository progressRepository;
    private final AlarmMapper alarmMapper;
    private final AlarmRepository alarmRepository;

    @Override
    @Transactional(readOnly = true)
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("매일 오후 10시 마다 실행 될 작업 작성 공간");

        List<Order> orderList = orderRepository.findAllByStatus(1);

        for (Order o : orderList) {
            Progress progress = progressRepository.findByOrder(o);
            progress.update(ProgressStatus.수거진행중);
            Alarm alarm = alarmMapper.toAlarm(progress.getOrder().getUser(), AlarmType.PickupStart);
            alarmRepository.save(alarm);
        }

    }
}
