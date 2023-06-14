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
    private int mainCard;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardCompany;

    @Column(nullable = false)
    private String customerKey;

    @Column(nullable = false)
    private String billingKey;

    @Column(nullable = false, length = 1000)
    private String cardResponseData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;

    @Builder
    public Card(int mainCard, String cardNumber, String cardCompany, String customerKey, String billingKey, User user, String cardResponseData) {
        this.mainCard = mainCard;
        this.cardNumber = cardNumber;
        this.cardCompany = cardCompany;
        this.customerKey = customerKey;
        this.billingKey = billingKey;
        this.user = user;
        this.cardResponseData = cardResponseData;
    }

    public void representativeDesignate() {
        this.mainCard = 1;
    }

    public void delRepresentativeDesignate() {
        this.mainCard = 0;
    }
}
