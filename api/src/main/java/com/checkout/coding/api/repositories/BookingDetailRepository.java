package com.checkout.coding.api.repositories;

import com.checkout.coding.api.entities.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, String> {
}