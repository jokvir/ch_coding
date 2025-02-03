package com.checkout.coding.api.dto;

import java.util.List;

public record BookingRequest(
         String customerName,
         String email,
         String phone,
         int duration,
         List<AvailabilityRequest> availabilities
) {
}
