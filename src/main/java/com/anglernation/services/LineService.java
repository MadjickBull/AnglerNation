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
        line.setLineType(request.getLineType());
        line.setName(request.getName());
        line.setDiameter(request.getDiameter());
        line.setBreakingStrength(request.getBreakingStrength());
        line.setColor(request.getColor());
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

        if (request.getLineType() != null) line.setLineType(request.getLineType());
        if (request.getName() != null) line.setName(request.getName());
        if (request.getDiameter() != null) line.setDiameter(request.getDiameter());
        if (request.getBreakingStrength() != null) line.setBreakingStrength(request.getBreakingStrength());
        if (request.getColor() != null) line.setColor(request.color());

        return toDto(lineRepo.save(line));
    }

    @Transactional
    public LineDto deleteLine(UUID lineId) {
        Line line = getLineOrThrow(lineId);
        lineRepo.delete(line);
        return toDto(line);
    }

    private LineDto toDto(Line line) {
        LineDto dto = new LineDto();
        dto.setId(line.getId());
        dto.setLineType(line.getLineType());
        dto.setName(line.getName());
        dto.setDiameter(line.getDiameter());
        dto.setBreakingStrength(line.getBreakingStrength());
        dto.setColor(line.getColor());
        dto.setOwnerId(line.getOwner().getId());
        return dto;
    }
}