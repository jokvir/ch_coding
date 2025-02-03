package com.checkout.coding.api.repositories;

import com.checkout.coding.api.entities.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, String> {
}