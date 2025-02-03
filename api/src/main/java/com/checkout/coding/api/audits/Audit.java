package com.checkout.coding.api.audits;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Audit(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {
}
