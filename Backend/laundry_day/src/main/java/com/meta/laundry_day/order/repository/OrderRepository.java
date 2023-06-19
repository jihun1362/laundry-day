package com.meta.laundry_day.order.repository;

import com.meta.laundry_day.order.entity.Order;
import com.meta.laundry_day.order.entity.Progress;
import com.meta.laundry_day.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
    Order findByProgress(Progress progress);
    List<Order> findAllByUserAndPaymentDoneOrderByCreatedAtDesc(User user, int i);
}
