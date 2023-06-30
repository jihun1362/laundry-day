package com.meta.laundry_day.payment.repository;

import com.meta.laundry_day.payment.entity.UserPoint;
import com.meta.laundry_day.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    List<UserPoint> findAllByUserOrderByCreatedAtDesc(User user);
}
