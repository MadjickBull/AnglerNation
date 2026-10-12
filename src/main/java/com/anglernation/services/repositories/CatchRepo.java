package com.anglernation.services.repositories;

import com.anglernation.models.catches.Catch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CatchRepo extends JpaRepository<Catch, UUID> {
    List<Catch> findByAnglerId(UUID anglerId);
}