package com.anglernation.services.repositories;

import com.anglernation.models.rods.Rod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RodRepo extends JpaRepository<Rod, UUID> {
    List<Rod> findByOwnerId(UUID ownerId);
}