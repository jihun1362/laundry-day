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
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class WashingType extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String typeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Admin_Id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "washingType", fetch = FetchType.LAZY)
    private final List<StablePricing> stablePricingList = new ArrayList<>();

    @Builder
    public WashingType(String typeName, User user) {
        this.typeName = typeName;
        this.user = user;
    }

    public void update(String typeName) {
        this.typeName = typeName;
    }
}
