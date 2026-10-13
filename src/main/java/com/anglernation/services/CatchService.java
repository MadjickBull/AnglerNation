package com.anglernation.services;

import com.anglernation.dtos.CatchDto;
import com.anglernation.dtos.CreateCatchRequest;
import com.anglernation.dtos.UpdateCatchRequest;
import com.anglernation.models.catches.Catch;
import com.anglernation.models.locations.Location;
import com.anglernation.models.rods.Rod;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.lines.Line;
import com.anglernation.models.Fish.Fish;
import com.anglernation.models.users.User;
import com.anglernation.services.repositories.CatchRepo;
import com.anglernation.services.repositories.LocationRepo;
import com.anglernation.services.repositories.RodRepo;
import com.anglernation.services.repositories.LureRepo;
import com.anglernation.services.repositories.LineRepo;
import com.anglernation.services.repositories.FishRepo;
import com.anglernation.services.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CatchService {

    private final CatchRepo catchRepo;
    private final LocationRepo locationRepo;
    private final RodRepo rodRepo;
    private final LureRepo lureRepo;
    private final LineRepo lineRepo;
    private final FishRepo fishRepo;
    private final UserRepo userRepo;

    public CatchService(CatchRepo catchRepo, LocationRepo locationRepo, RodRepo rodRepo,
                        LureRepo lureRepo, LineRepo lineRepo, FishRepo fishRepo, UserRepo userRepo) {
        this.catchRepo = catchRepo;
        this.locationRepo = locationRepo;
        this.rodRepo = rodRepo;
        this.lureRepo = lureRepo;
        this.lineRepo = lineRepo;
        this.fishRepo = fishRepo;
        this.userRepo = userRepo;
    }

    private Catch getCatchOrThrow(UUID catchId) {
        return catchRepo.findById(catchId)
                .orElseThrow(() -> new EntityNotFoundException("Catch not found"));
    }

    private Location getLocationOrThrow(UUID locationId) {
        return locationRepo.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    private Rod getRodOrThrow(UUID rodId) {
        return rodRepo.findById(rodId)
                .orElseThrow(() -> new EntityNotFoundException("Rod not found"));
    }

    private Lure getLureOrThrow(UUID lureId) {
        return lureRepo.findById(lureId)
                .orElseThrow(() -> new EntityNotFoundException("Lure not found"));
    }

    private Line getLineOrThrow(UUID lineId) {
        return lineRepo.findById(lineId)
                .orElseThrow(() -> new EntityNotFoundException("Line not found"));
    }

    private Fish getFishOrThrow(UUID fishId) {
        return fishRepo.findById(fishId)
                .orElseThrow(() -> new EntityNotFoundException("Fish not found"));
    }

    private User getUserOrThrow(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public CatchDto createCatch(CreateCatchRequest request, UUID anglerId) {
        User angler = getUserOrThrow(anglerId);
        Location location = getLocationOrThrow(request.locationId());
        Fish fish = getFishOrThrow(request.fishId());

        Rod rod = request.rodId() != null ? getRodOrThrow(request.rodId()) : null;
        Lure lure = request.lureId() != null ? getLureOrThrow(request.lureId()) : null;
        Line line = request.lineId() != null ? getLineOrThrow(request.lineId()) : null;

        Catch newCatch = new Catch();
        newCatch.setLocation(location);
        newCatch.setRod(rod);
        newCatch.setLure(lure);
        newCatch.setLine(line);
        newCatch.setFish(fish);
        newCatch.setAngler(angler);

        catchRepo.save(newCatch);
        return toDto(newCatch);
    }

    public CatchDto getCatchById(UUID catchId) {
        return toDto(getCatchOrThrow(catchId));
    }

    public List<CatchDto> getAllCatches() {
        return catchRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CatchDto> getCatchesByAnglerId(UUID anglerId) {
        getUserOrThrow(anglerId);
        return catchRepo.findByAnglerId(anglerId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CatchDto updateCatch(UUID catchId, UpdateCatchRequest request) {
        Catch existingCatch = getCatchOrThrow(catchId);

        if (request.locationId() != null) {
            existingCatch.setLocation(getLocationOrThrow(request.locationId()));
        }
        if (request.rodId() != null) {
            existingCatch.setRod(getRodOrThrow(request.rodId()));
        } else {
            existingCatch.setRod(null);
        }
        if (request.lureId() != null) {
            existingCatch.setLure(getLureOrThrow(request.lureId()));
        } else {
            existingCatch.setLure(null);
        }
        if (request.lineId() != null) {
            existingCatch.setLine(getLineOrThrow(request.lineId()));
        } else {
            existingCatch.setLine(null);
        }
        if (request.fishId() != null) {
            existingCatch.setFish(getFishOrThrow(request.fishId()));
        }

        return toDto(catchRepo.save(existingCatch));
    }

    @Transactional
    public CatchDto deleteCatch(UUID catchId) {
        Catch existingCatch = getCatchOrThrow(catchId);
        catchRepo.delete(existingCatch);
        return toDto(existingCatch);
    }

    private CatchDto toDto(Catch catchEntity) {
        return new CatchDto(
                catchEntity.getId(),
                catchEntity.getLocation().getId(),
                catchEntity.getRod() != null ? catchEntity.getRod().getId() : null,
                catchEntity.getLure() != null ? catchEntity.getLure().getId() : null,
                catchEntity.getLine() != null ? catchEntity.getLine().getId() : null,
                catchEntity.getFish().getId(),
                catchEntity.getAngler().getId(),
                catchEntity.getCreatedAt()
        );
    }
}