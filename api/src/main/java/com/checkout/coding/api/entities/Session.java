package com.checkout.coding.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private SessionType type;
    private BigDecimal price;

    public enum SessionType {
        Padel, Fitness, Tennis
    }


}
