package com.meta.laundry_day.address_details.repository;

import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDetailRepository extends JpaRepository<AddressDetails, Long> {
    AddressDetails findByUser(User user);
}
