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
        location.setName(request.getName());
        location.setVisibility(request.getVisibility());
        location.setAltitude(request.getAltitude());
        location.setLongitude(request.getLongitude());
        location.setLatitude(request.getLatitude());

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

        if (request.getName() != null) location.setName(request.getName());
        if (request.getVisibility() != null) location.setVisibility(request.getVisibility());
        if (request.getAltitude() != null) location.setAltitude(request.getAltitude());
        if (request.getLongitude() != null) location.setLongitude(request.getLongitude());
        if (request.getLatitude() != null) location.setLatitude(request.getLatitude());

        return toDto(locationRepo.save(location));
    }

    @Transactional
    public LocationDto deleteLocation(UUID locationId) {
        Location location = getLocationOrThrow(locationId);
        locationRepo.delete(location);
        return toDto(location);
    }

    private LocationDto toDto(Location location) {
        LocationDto dto = new LocationDto();
        dto.setId(location.getId());
        dto.setName(location.getName());
        dto.setVisibility(location.getVisibility());
        dto.setAltitude(location.getAltitude());
        dto.setLongitude(location.getLongitude());
        dto.setLatitude(location.getLatitude());
        return dto;
    }
}