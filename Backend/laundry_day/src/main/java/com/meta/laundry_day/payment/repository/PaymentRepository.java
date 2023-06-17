package com.meta.laundry_day.payment.repository;

import com.meta.laundry_day.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderId(Long id);
}
