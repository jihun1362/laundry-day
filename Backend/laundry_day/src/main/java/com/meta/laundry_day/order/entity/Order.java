package com.meta.laundry_day.order.entity;

import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.common.TimeStamped;
import com.meta.laundry_day.payment.entity.PaymentDtails;
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
import javax.persistence.OneToOne;

@Entity(name = "orders")
@Getter
@NoArgsConstructor
public class Order extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LaundryType laundryType;

    @Column(nullable = false)
    private String washingMethod;

    @Column(nullable = false)
    private int consentToNotice = 1;

    @Column
    private String orderRequest;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private int status = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AddressDetails_Id", nullable = false)
    private AddressDetails addressDetails;

    @OneToOne(mappedBy = "order",fetch = FetchType.EAGER)
    private PaymentDtails paymentDtails;

    @OneToOne(mappedBy = "order",fetch = FetchType.LAZY)
    private Progress progress;

    @Builder
    public Order(LaundryType laundryType, String washingMethod, int consentToNotice, String orderRequest, PaymentMethod paymentMethod, int status, User user, AddressDetails addressDetails, PaymentDtails paymentDtails, Progress progress) {
        this.laundryType = laundryType;
        this.washingMethod = washingMethod;
        this.consentToNotice = consentToNotice;
        this.orderRequest = orderRequest;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.user = user;
        this.addressDetails = addressDetails;
    }
}
