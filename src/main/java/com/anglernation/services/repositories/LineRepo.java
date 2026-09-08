package com.anglernation.services.repositories;

import com.anglernation.models.lines.Line;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LineRepo extends JpaRepository<Line , UUID> {
}
