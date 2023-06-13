package com.meta.laundry_day.stable_pricing.repository;

import com.meta.laundry_day.stable_pricing.entity.StablePricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StablePricingRepository extends JpaRepository<StablePricing, Long> {
}
