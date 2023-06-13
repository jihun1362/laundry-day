package com.meta.laundry_day.event_details.entity;

import com.meta.laundry_day.common.TimeStamped;
import com.meta.laundry_day.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class EventDetails extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String image;

    @Column
    private Long accumulatePoint;

    @Column
    private Double discountRate;

    @Column(nullable = false)
    private String until;

    @Column(nullable = false)
    private int status = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;

    @Builder
    public EventDetails(String title, String content, String image, Long accumulatePoint, Double discountRate, String until, int status, User user) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.accumulatePoint = accumulatePoint;
        this.discountRate = discountRate;
        this.until = until;
        this.status = status;
        this.user = user;
    }
}
