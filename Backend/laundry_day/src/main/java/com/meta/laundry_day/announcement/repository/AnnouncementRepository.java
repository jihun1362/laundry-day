package com.meta.laundry_day.announcement.repository;

import com.meta.laundry_day.announcement.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
