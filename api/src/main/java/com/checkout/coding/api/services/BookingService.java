package com.checkout.coding.api.services;

import com.checkout.coding.api.dto.BookingRequest;
import com.checkout.coding.api.entities.Booking;

public interface BookingService {
    Booking createBooking(BookingRequest request);
}
