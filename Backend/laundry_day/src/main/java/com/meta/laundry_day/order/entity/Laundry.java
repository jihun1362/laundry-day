package com.meta.laundry_day.order.entity;

import com.meta.laundry_day.common.TimeStamped;
import com.meta.laundry_day.stable_pricing.entity.StablePricing;
import com.meta.laundry_day.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class Laundry extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String image;

    @Column
    private String surchargeDetail;

    @Column
    private Long surcharge;

    @Column(nullable = false)
    private String clothesType;

    @Column(nullable = false)
    private Long stablePrice;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LaundryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Admin_Id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Progress_Id", nullable = false)
    private Progress progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StablePricing_Id")
    private StablePricing stablePricing;

    @Builder
    public Laundry(String image, String surchargeDetail, Long surcharge, String clothesType, Long stablePrice, LaundryStatus status, User user, Progress progress, StablePricing stablePricing) {
        this.image = image;
        this.surchargeDetail = surchargeDetail;
        this.surcharge = surcharge;
        this.clothesType = clothesType;
        this.stablePrice = stablePrice;
        this.status = status;
        this.user = user;
        this.progress = progress;
        this.stablePricing = stablePricing;
    }

    public void update(LaundryStatus status) {
        this.status = status;
    }
}
