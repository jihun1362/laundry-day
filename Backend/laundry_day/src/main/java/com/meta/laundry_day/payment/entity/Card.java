package com.meta.laundry_day.payment.entity;

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
public class Card extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int mainCard = 0;

    @Column(nullable = false)
    private String cardNumber;

    @Column(name = "expiration_Date_M",nullable = false)
    private String expirationDateM;

    @Column(name = "expiration_Date_Y",nullable = false)
    private String expirationDateY;

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private String billingKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;

    @Builder
    public Card(int mainCard, String cardNumber, String expirationDateM, String expirationDateY, String birthDate, String billingKey, User user) {
        this.mainCard = mainCard;
        this.cardNumber = cardNumber;
        this.expirationDateM = expirationDateM;
        this.expirationDateY = expirationDateY;
        this.birthDate = birthDate;
        this.billingKey = billingKey;
        this.user = user;
    }
}
