package com.anglernation.services;

import com.anglernation.dtos.CreateRodRequest;
import com.anglernation.dtos.RodDto;
import com.anglernation.dtos.UpdateRodRequest;
import com.anglernation.models.rods.Rod;
import com.anglernation.models.users.User;
import com.anglernation.services.repositories.RodRepo;
import com.anglernation.services.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RodService {

    private final RodRepo rodRepo;
    private final UserRepo userRepo;

    public RodService(RodRepo rodRepo, UserRepo userRepo) {
        this.rodRepo = rodRepo;
        this.userRepo = userRepo;
    }

    private Rod getRodOrThrow(UUID rodId) {
        return rodRepo.findById(rodId)
                .orElseThrow(() -> new EntityNotFoundException("Rod not found"));
    }

    private User getUserOrThrow(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public RodDto createRod(CreateRodRequest request, UUID ownerId) {
        User owner = getUserOrThrow(ownerId);

        Rod rod = new Rod();
        rod.setRodType(request.getRodType());
        rod.setName(request.getName());
        rod.setLength(request.getLength());
        rod.setWeight(request.getWeight());
        rod.setRodPower(request.getRodPower());
        rod.setRodAction(request.getRodAction());
        rod.setOwner(owner);

        rodRepo.save(rod);
        return toDto(rod);
    }

    public RodDto getRodById(UUID rodId) {
        return toDto(getRodOrThrow(rodId));
    }

    public List<RodDto> getAllRods() {
        return rodRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<RodDto> getRodsByOwnerId(UUID ownerId) {
        getUserOrThrow(ownerId);
        return rodRepo.findByOwnerId(ownerId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RodDto updateRod(UUID rodId, UpdateRodRequest request) {
        Rod rod = getRodOrThrow(rodId);

        if (request.getRodType() != null) rod.setRodType(request.getRodType());
        if (request.getName() != null) rod.setName(request.getName());
        if (request.getLength() != null) rod.setLength(request.getLength());
        if (request.getWeight() != null) rod.setWeight(request.getWeight());
        if (request.getRodPower() != null) rod.setRodPower(request.getRodPower());
        if (request.getRodAction() != null) rod.setRodAction(request.getRodAction());

        return toDto(rodRepo.save(rod));
    }

    @Transactional
    public RodDto deleteRod(UUID rodId) {
        Rod rod = getRodOrThrow(rodId);
        rodRepo.delete(rod);
        return toDto(rod);
    }

    private RodDto toDto(Rod rod) {
        RodDto dto = new RodDto();
        dto.setId(rod.getId());
        dto.setRodType(rod.getRodType());
        dto.setName(rod.getName());
        dto.setLength(rod.getLength());
        dto.setWeight(rod.getWeight());
        dto.setRodPower(rod.getRodPower());
        dto.setRodAction(rod.getRodAction());
        dto.setOwnerId(rod.getOwner().getId());
        return dto;
    }
}