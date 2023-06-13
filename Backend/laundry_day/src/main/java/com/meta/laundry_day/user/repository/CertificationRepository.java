package com.meta.laundry_day.user.repository;

import com.meta.laundry_day.user.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
