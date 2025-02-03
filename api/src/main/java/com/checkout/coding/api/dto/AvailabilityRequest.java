package com.checkout.coding.api.dto;

import java.time.LocalDateTime;

public record AvailabilityRequest(
         LocalDateTime startTime,
         LocalDateTime endTime,
         String sessionId
) {
}
