package com.anglernation.services.repositories;

import com.anglernation.models.locations.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepo extends JpaRepository<Location , UUID> {
}
