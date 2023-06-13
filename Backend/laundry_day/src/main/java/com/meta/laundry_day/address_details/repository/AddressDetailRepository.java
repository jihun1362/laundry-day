package com.meta.laundry_day.address_details.repository;

import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressDetailRepository extends JpaRepository<AddressDetails, Long> {
    List<AddressDetails> findAllByUser(User user);
}
