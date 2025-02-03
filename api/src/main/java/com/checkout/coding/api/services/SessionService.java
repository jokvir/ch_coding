package com.checkout.coding.api.services;

import com.checkout.coding.api.dto.SessionResponse;

import java.util.List;

public interface SessionService {
    List<SessionResponse> getSessions();
}
