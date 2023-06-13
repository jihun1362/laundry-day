package com.meta.laundry_day.address_details.repository;

import com.meta.laundry_day.address_details.entity.AddressDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDetailRepository extends JpaRepository<AddressDetails, Long> {
}
