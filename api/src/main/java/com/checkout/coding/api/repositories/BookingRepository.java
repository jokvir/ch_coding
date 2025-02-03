package com.checkout.coding.api.repositories;

import com.checkout.coding.api.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
}