package com.meta.laundry_day.payment.repository;

import com.meta.laundry_day.payment.entity.PaymentDtails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDtailsRepository extends JpaRepository<PaymentDtails, Long> {
}
