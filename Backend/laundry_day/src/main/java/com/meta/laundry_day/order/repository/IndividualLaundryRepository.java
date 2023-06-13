package com.meta.laundry_day.order.repository;

import com.meta.laundry_day.order.entity.IndividualLaundry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualLaundryRepository extends JpaRepository<IndividualLaundry, Long> {
}
