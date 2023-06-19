package com.meta.laundry_day.alarm.repository;

import com.meta.laundry_day.alarm.entity.Alarm;
import com.meta.laundry_day.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByUserOrderByCreatedAtDesc(User user);
}
