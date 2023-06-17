package com.meta.laundry_day.event_details.repository;

import com.meta.laundry_day.event_details.entity.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDetailsRepository extends JpaRepository<EventDetails, Long> {
    List<EventDetails> findAllByOrderByCreatedAtDesc();
    EventDetails findAllTopByOrderByDiscountRateDesc();
}
