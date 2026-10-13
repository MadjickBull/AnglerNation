package com.anglernation.services;

import com.anglernation.dtos.CreateLocationRequest;
import com.anglernation.dtos.LocationDto;
import com.anglernation.dtos.UpdateLocationRequest;
import com.anglernation.models.locations.Location;
import com.anglernation.models.locations.LocationVisibility;
import com.anglernation.services.repositories.LocationRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepo locationRepo;

    public LocationService(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    private Location getLocationOrThrow(UUID locationId) {
        return locationRepo.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    @Transactional
    public LocationDto createLocation(CreateLocationRequest request) {
        Location location = new Location();
        location.setName(request.name());
        location.setVisibility(request.visibility());
        location.setAltitude(request.altitude());
        location.setLongitude(request.longitude());
        location.setLatitude(request.latitude());

        locationRepo.save(location);
        return toDto(location);
    }

    public LocationDto getLocationById(UUID locationId) {
        return toDto(getLocationOrThrow(locationId));
    }

    public List<LocationDto> getAllLocations() {
        return locationRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public LocationDto updateLocation(UUID locationId, UpdateLocationRequest request) {
        Location location = getLocationOrThrow(locationId);

        if (request.name() != null) location.setName(request.name());
        if (request.visibility() != null) location.setVisibility(request.visibility());
        if (request.altitude() != null) location.setAltitude(request.altitude());
        if (request.longitude() != null) location.setLongitude(request.longitude());
        if (request.latitude() != null) location.setLatitude(request.latitude());

        return toDto(locationRepo.save(location));
    }

    @Transactional
    public LocationDto deleteLocation(UUID locationId) {
        Location location = getLocationOrThrow(locationId);
        locationRepo.delete(location);
        return toDto(location);
    }

    private LocationDto toDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getName(),
                location.getVisibility(),
                location.getAltitude(),
                location.getLongitude(),
                location.getLatitude()
        );
    }
}