package com.anglernation.services;

import com.anglernation.dtos.CreateLineRequest;
import com.anglernation.dtos.LineDto;
import com.anglernation.dtos.UpdateLineRequest;
import com.anglernation.models.lines.Line;
import com.anglernation.models.users.User;
import com.anglernation.services.repositories.LineRepo;
import com.anglernation.services.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LineService {

    private final LineRepo lineRepo;
    private final UserRepo userRepo;

    public LineService(LineRepo lineRepo, UserRepo userRepo) {
        this.lineRepo = lineRepo;
        this.userRepo = userRepo;
    }

    private Line getLineOrThrow(UUID lineId) {
        return lineRepo.findById(lineId)
                .orElseThrow(() -> new EntityNotFoundException("Line not found"));
    }

    private User getUserOrThrow(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public LineDto createLine(CreateLineRequest request, UUID ownerId) {
        User owner = getUserOrThrow(ownerId);

        Line line = new Line();
        line.setLineType(request.lineType());
        line.setName(request.name());
        line.setDiameter(request.diameter());
        line.setBreakingStrength(request.breakingStrength());
        line.setColor(request.color());
        line.setOwner(owner);

        lineRepo.save(line);
        return toDto(line);
    }

    public LineDto getLineById(UUID lineId) {
        return toDto(getLineOrThrow(lineId));
    }

    public List<LineDto> getAllLines() {
        return lineRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<LineDto> getLinesByOwnerId(UUID ownerId) {
        getUserOrThrow(ownerId);
        return lineRepo.findByOwnerId(ownerId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public LineDto updateLine(UUID lineId, UpdateLineRequest request) {
        Line line = getLineOrThrow(lineId);

        if (request.lineType() != null) line.setLineType(request.lineType());
        if (request.name() != null) line.setName(request.name());
        if (request.diameter() != null) line.setDiameter(request.diameter());
        if (request.breakingStrength() != null) line.setBreakingStrength(request.breakingStrength());
        if (request.color() != null) line.setColor(request.color());

        return toDto(lineRepo.save(line));
    }

    @Transactional
    public LineDto deleteLine(UUID lineId) {
        Line line = getLineOrThrow(lineId);
        lineRepo.delete(line);
        return toDto(line);
    }

    private LineDto toDto(Line line) {
        return new LineDto(
                line.getId(),
                line.getLineType(),
                line.getName(),
                line.getDiameter(),
                line.getBreakingStrength(),
                line.getColor(),
                line.getOwner().getId()
        );
    }
}