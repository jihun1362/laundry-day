package com.meta.laundry_day.payment.repository;

import com.meta.laundry_day.payment.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
}
