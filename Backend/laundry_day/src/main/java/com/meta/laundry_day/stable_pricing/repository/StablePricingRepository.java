package com.meta.laundry_day.stable_pricing.repository;

import com.meta.laundry_day.stable_pricing.entity.StablePricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StablePricingRepository extends JpaRepository<StablePricing, Long> {
    List<StablePricing> findAllByWashingTypeId(Long id);

    @Modifying
    @Query("delete from StablePricing s where s in :stablePricingList")
    void deleteAllByInQuery(@Param("stablePricingList") List<StablePricing> stablePricingList);
}
