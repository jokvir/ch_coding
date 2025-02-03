package com.checkout.coding.api.services;

import com.checkout.coding.api.entities.Availability;
import org.springframework.data.domain.Page;

public interface AvailabilityService {
    Page<Availability> getAvailableSlots();

    Page<Availability> getAvailableSlotsBySession(String sessionId);
}
