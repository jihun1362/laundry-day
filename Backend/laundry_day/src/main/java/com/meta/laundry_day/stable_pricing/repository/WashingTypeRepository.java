package com.meta.laundry_day.stable_pricing.repository;

import com.meta.laundry_day.stable_pricing.entity.WashingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WashingTypeRepository extends JpaRepository<WashingType, Long> {
}
