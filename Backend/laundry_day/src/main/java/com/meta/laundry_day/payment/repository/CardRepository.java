package com.meta.laundry_day.payment.repository;

import com.meta.laundry_day.payment.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
