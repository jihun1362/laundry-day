package com.meta.laundry_day.inquiry.repository;

import com.meta.laundry_day.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
