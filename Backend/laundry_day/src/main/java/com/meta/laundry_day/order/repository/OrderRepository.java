package com.meta.laundry_day.order.repository;

import com.meta.laundry_day.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
