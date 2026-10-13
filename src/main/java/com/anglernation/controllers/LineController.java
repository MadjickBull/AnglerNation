package com.anglernation.controllers;

import com.anglernation.dtos.CreateLineRequest;
import com.anglernation.dtos.LineDto;
import com.anglernation.dtos.UpdateLineRequest;
import com.anglernation.services.LineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lines")
public class LineController {

    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping
    public ResponseEntity<LineDto> createLine(@Valid @RequestBody CreateLineRequest request, @RequestParam UUID ownerId) {
        LineDto created = lineService.createLine(request, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineDto> getLineById(@PathVariable UUID id) {
        return ResponseEntity.ok(lineService.getLineById(id));
    }

    @GetMapping
    public ResponseEntity<List<LineDto>> getAllLines() {
        return ResponseEntity.ok(lineService.getAllLines());
    }

    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<LineDto>> getLinesByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(lineService.getLinesByOwnerId(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineDto> updateLine(@PathVariable UUID id, @Valid @RequestBody UpdateLineRequest request) {
        return ResponseEntity.ok(lineService.updateLine(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LineDto> deleteLine(@PathVariable UUID id) {
        return ResponseEntity.ok(lineService.deleteLine(id));
    }
}