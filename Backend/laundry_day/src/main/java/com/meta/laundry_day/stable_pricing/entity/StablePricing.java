package com.meta.laundry_day.stable_pricing.entity;

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
public class StablePricing extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clothesType;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Admin_Id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WashingType_Id", nullable = false)
    private WashingType washingType;

    @Builder
    public StablePricing(String clothesType, Long price, User user, WashingType washingType) {
        this.clothesType = clothesType;
        this.price = price;
        this.user = user;
        this.washingType = washingType;
    }

    public void update(String clothesType, Long price) {
        this.clothesType = clothesType;
        this.price = price;
    }
}
