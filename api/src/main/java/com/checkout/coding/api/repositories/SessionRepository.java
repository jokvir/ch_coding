package com.checkout.coding.api.repositories;

import com.checkout.coding.api.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {
}