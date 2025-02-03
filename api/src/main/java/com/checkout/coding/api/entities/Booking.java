package com.checkout.coding.api.entities;


import com.checkout.coding.api.audits.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails;
}
