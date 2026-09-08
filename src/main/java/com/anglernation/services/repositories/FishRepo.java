package com.anglernation.services.repositories;

import com.anglernation.models.Fish.Fish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FishRepo extends JpaRepository<Fish , UUID> {
}
