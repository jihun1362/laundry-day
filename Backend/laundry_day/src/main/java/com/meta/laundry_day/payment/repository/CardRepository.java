package com.meta.laundry_day.payment.repository;

import com.meta.laundry_day.payment.entity.Card;
import com.meta.laundry_day.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByUser(User user);
    Card findByUserAndMainCard(User user, int i);
}
