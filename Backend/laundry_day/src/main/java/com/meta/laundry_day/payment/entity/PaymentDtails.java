package com.meta.laundry_day.payment.entity;

import com.meta.laundry_day.common.TimeStamped;
import com.meta.laundry_day.order.entity.Order;
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
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
public class PaymentDtails extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long basicAmount;

    @Column
    private Double usePoint;

    @Column
    private Double discountRate;

    @Column(nullable = false)
    private Long deliveryFee;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String customerKey;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardCompany;

    @Column(nullable = false, length = 5000)
    private String receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Card_Id")
    private Card card;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_Id", nullable = false)
    private Order order;

    @Builder
    public PaymentDtails(Long userId, Long basicAmount, Double usePoint, Double discountRate, Long deliveryFee, Double totalAmount, String customerKey, String cardNumber, String cardCompany, String receipt, Card card, Order order) {
        this.userId = userId;
        this.basicAmount = basicAmount;
        this.usePoint = usePoint;
        this.discountRate = discountRate;
        this.deliveryFee = deliveryFee;
        this.totalAmount = totalAmount;
        this.customerKey = customerKey;
        this.cardNumber = cardNumber;
        this.cardCompany = cardCompany;
        this.receipt = receipt;
        this.card = card;
        this.order = order;
    }
}
