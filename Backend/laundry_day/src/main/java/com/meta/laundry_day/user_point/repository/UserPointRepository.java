package com.meta.laundry_day.user_point.repository;

import com.meta.laundry_day.user_point.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
}
