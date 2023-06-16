package com.meta.laundry_day.order.entity;

import com.meta.laundry_day.common.TimeStamped;
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
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
public class Progress extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProgressStatus status;

    @Column(nullable = false)
    private int laundryRegist;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_Id", nullable = false)
    private Order order;

    @Builder
    public Progress(Long userId, ProgressStatus status, Order order, int laundryRegist) {
        this.userId = userId;
        this.status = status;
        this.laundryRegist = laundryRegist;
        this.order = order;
    }

    public void doneRegist() {
        this.laundryRegist = 0;
    }

    public void update(ProgressStatus status) {
        this.status=status;
    }
}
