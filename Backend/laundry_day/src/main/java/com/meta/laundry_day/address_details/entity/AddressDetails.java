package com.meta.laundry_day.address_details.entity;

import com.meta.laundry_day.address_details.dto.AddressRequestDto;
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
public class AddressDetails extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column
    private String significant;

    @Column
    private String accessMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;

    @Builder
    public AddressDetails(String address, String significant, String accessMethod, User user) {
        this.address = address;
        this.significant = significant;
        this.accessMethod = accessMethod;
        this.user = user;
    }

    public void update(AddressRequestDto addressRequestDto) {
        this.address = addressRequestDto.getAddress();
        this.significant = addressRequestDto.getSignificant();
        this.accessMethod = addressRequestDto.getAccessMethod();
    }
}
