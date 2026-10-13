package com.anglernation.services;

import com.anglernation.dtos.CreateFishRequest;
import com.anglernation.dtos.FishDto;
import com.anglernation.dtos.UpdateFishRequest;
import com.anglernation.models.Fish.Fish;
import com.anglernation.services.repositories.FishRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FishService {

    private final FishRepo fishRepo;

    public FishService(FishRepo fishRepo) {
        this.fishRepo = fishRepo;
    }

    private Fish getFishOrThrow(UUID fishId) {
        return fishRepo.findById(fishId)
                .orElseThrow(() -> new EntityNotFoundException("Fish not found"));
    }

    @Transactional
    public FishDto createFish(CreateFishRequest request) {
        Fish fish = new Fish();
        fish.setFishType(request.fishType());
        fish.setName(request.name());
        fish.setWeight(request.weight());
        fish.setSize(request.size());

        fishRepo.save(fish);
        return toDto(fish);
    }

    public FishDto getFishById(UUID fishId) {
        return toDto(getFishOrThrow(fishId));
    }

    public List<FishDto> getAllFish() {
        return fishRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public FishDto updateFish(UUID fishId, UpdateFishRequest request) {
        Fish fish = getFishOrThrow(fishId);

        if (request.fishType() != null) fish.setFishType(request.fishType());
        if (request.name() != null) fish.setName(request.name());
        if (request.weight() != null) fish.setWeight(request.weight());
        if (request.size() != null) fish.setSize(request.size());

        return toDto(fishRepo.save(fish));
    }

    @Transactional
    public FishDto deleteFish(UUID fishId) {
        Fish fish = getFishOrThrow(fishId);
        fishRepo.delete(fish);
        return toDto(fish);
    }

    private FishDto toDto(Fish fish) {
        return new FishDto(
                fish.getId(),
                fish.getFishType(),
                fish.getName(),
                fish.getWeight(),
                fish.getSize()
        );
    }
}