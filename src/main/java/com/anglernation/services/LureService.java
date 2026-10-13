package com.anglernation.services;

import com.anglernation.dtos.CreateLureRequest;
import com.anglernation.dtos.LureDto;
import com.anglernation.dtos.UpdateLureRequest;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.users.User;
import com.anglernation.services.repositories.LureRepo;
import com.anglernation.services.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LureService {

    private final LureRepo lureRepo;
    private final UserRepo userRepo;

    public LureService(LureRepo lureRepo, UserRepo userRepo) {
        this.lureRepo = lureRepo;
        this.userRepo = userRepo;
    }

    private Lure getLureOrThrow(UUID lureId) {
        return lureRepo.findById(lureId)
                .orElseThrow(() -> new EntityNotFoundException("Lure not found"));
    }

    private User getUserOrThrow(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public LureDto createLure(CreateLureRequest request, UUID ownerId) {
        User owner = getUserOrThrow(ownerId);

        Lure lure = new Lure();
        lure.setLureType(request.lureType());
        lure.setName(request.name());
        lure.setColor(request.color());
        lure.setSize(request.size());
        lure.setWeight(request.weight());
        lure.setLureBuoyancy(request.lureBuoyancy());
        lure.setOwner(owner);

        lureRepo.save(lure);
        return toDto(lure);
    }

    public LureDto getLureById(UUID lureId) {
        return toDto(getLureOrThrow(lureId));
    }

    public List<LureDto> getAllLures() {
        return lureRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<LureDto> getLuresByOwnerId(UUID ownerId) {
        getUserOrThrow(ownerId);
        return lureRepo.findByOwnerId(ownerId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public LureDto updateLure(UUID lureId, UpdateLureRequest request) {
        Lure lure = getLureOrThrow(lureId);

        if (request.lureType() != null) lure.setLureType(request.lureType());
        if (request.name() != null) lure.setName(request.name());
        if (request.color() != null) lure.setColor(request.color());
        if (request.size() != null) lure.setSize(request.size());
        if (request.weight() != null) lure.setWeight(request.weight());
        if (request.lureBuoyancy() != null) lure.setLureBuoyancy(request.lureBuoyancy());

        return toDto(lureRepo.save(lure));
    }

    @Transactional
    public LureDto deleteLure(UUID lureId) {
        Lure lure = getLureOrThrow(lureId);
        lureRepo.delete(lure);
        return toDto(lure);
    }

    private LureDto toDto(Lure lure) {
        return new LureDto(
                lure.getId(),
                lure.getLureType(),
                lure.getName(),
                lure.getColor(),
                lure.getSize(),
                lure.getWeight(),
                lure.getLureBuoyancy(),
                lure.getOwner().getId()
        );
    }
}