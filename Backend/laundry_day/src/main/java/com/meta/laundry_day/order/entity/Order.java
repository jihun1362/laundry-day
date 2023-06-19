package com.meta.laundry_day.order.entity;

import com.meta.laundry_day.address_details.entity.AddressDetails;
import com.meta.laundry_day.common.TimeStamped;
import com.meta.laundry_day.payment.entity.Card;
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
    private String address;

    @Column
    private String orderRequest;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private int usePointCheck;

    @Column(nullable = false)
    private int paymentDone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AddressDetails_Id")
    private AddressDetails addressDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Card_Id")
    private Card card;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private PaymentDtails paymentDtails;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Progress progress;

    @Builder
    public Order(LaundryType laundryType, String washingMethod, String address, String orderRequest, int status, int usePointCheck, int paymentDone, User user, AddressDetails addressDetails, Card card) {
        this.laundryType = laundryType;
        this.washingMethod = washingMethod;
        this.address = address;
        this.status = status;
        this.usePointCheck = usePointCheck;
        this.paymentDone = paymentDone;
        this.orderRequest = orderRequest;
        this.user = user;
        this.addressDetails = addressDetails;
        this.card = card;
    }

    public void setPaymentDtails(PaymentDtails paymentDtails) {
        this.paymentDtails = paymentDtails;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public void doneOrder() {
        this.status = 0;
    }

    public void setPaymentDone() {
        this.paymentDone = 0;
    }
}
