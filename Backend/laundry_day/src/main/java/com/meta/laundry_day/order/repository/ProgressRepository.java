package com.meta.laundry_day.order.repository;

import com.meta.laundry_day.order.entity.Order;
import com.meta.laundry_day.order.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Progress findByOrder(Order order);
}
