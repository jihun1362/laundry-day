package com.meta.laundry_day.questions.repository;

import com.meta.laundry_day.questions.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {
}
