package com.meta.laundry_day.order.repository;

import com.meta.laundry_day.order.entity.Laundry;
import com.meta.laundry_day.order.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaundryRepository extends JpaRepository<Laundry, Long> {
    List<Laundry> findAllByProgress(Progress progress);
}
