package com.checkout.coding.api.dto;

import java.math.BigDecimal;

public record SessionResponse(String sessionId, String sessionType, BigDecimal price ) {
}
