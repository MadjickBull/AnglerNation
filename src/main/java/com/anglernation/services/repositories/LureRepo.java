package com.anglernation.services.repositories;

import com.anglernation.models.lures.Lure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LureRepo extends JpaRepository<Lure, UUID> {
}
