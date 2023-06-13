package com.meta.laundry_day.alarm.repository;

import com.meta.laundry_day.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
