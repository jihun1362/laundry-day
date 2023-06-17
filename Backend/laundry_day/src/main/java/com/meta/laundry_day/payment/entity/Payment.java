package com.meta.laundry_day.payment.entity;

import com.meta.laundry_day.common.TimeStamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Payment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long totalStablePrice;

    @Column(nullable = false)
    private Long totalStablePriceSurcharge;

    @Column(nullable = false)
    private Long amount;

    @Column
    private Double usePoint;

    @Column
    private Double discountRate;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long deliveryFee;

    @Column(nullable = false)
    private Double totalAmount;

    @Builder
    public Payment(Long totalStablePrice, Long totalStablePriceSurcharge, Long amount, Double usePoint, Double discountRate, Long orderId, Long deliveryFee, Double totalAmount) {
        this.totalStablePrice = totalStablePrice;
        this.totalStablePriceSurcharge = totalStablePriceSurcharge;
        this.amount = amount;
        this.usePoint = usePoint;
        this.discountRate = discountRate;
        this.orderId = orderId;
        this.deliveryFee = deliveryFee;
        this.totalAmount = totalAmount;
    }
}
