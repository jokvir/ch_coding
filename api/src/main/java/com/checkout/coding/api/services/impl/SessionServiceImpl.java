package com.checkout.coding.api.services.impl;

import com.checkout.coding.api.dto.SessionResponse;
import com.checkout.coding.api.entities.Session;
import com.checkout.coding.api.repositories.SessionRepository;
import com.checkout.coding.api.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public List<SessionResponse> getSessions() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions.stream().map(session -> new SessionResponse(session.getId(), String.valueOf(session.getType()), session.getPrice())).toList();
    }

    @Bean
    public int saveDefaultSession() {
        if (sessionRepository.findAll().isEmpty()) {
            Session padel = Session.builder().price(BigDecimal.valueOf(30)).type(Session.SessionType.Padel).build();
            Session Fitness = Session.builder().price(BigDecimal.valueOf(45)).type(Session.SessionType.Tennis).build();
            Session Tennis = Session.builder().price(BigDecimal.valueOf(15)).type(Session.SessionType.Tennis).build();
            sessionRepository.saveAll(List.of(padel, Fitness, Tennis));
        }
        return 0;

    }

}
