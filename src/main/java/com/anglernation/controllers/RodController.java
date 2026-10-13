package com.anglernation.controllers;

import com.anglernation.dtos.CreateRodRequest;
import com.anglernation.dtos.RodDto;
import com.anglernation.dtos.UpdateRodRequest;
import com.anglernation.services.RodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rods")
public class RodController {

    private final RodService rodService;

    public RodController(RodService rodService) {
        this.rodService = rodService;
    }

    @PostMapping
    public ResponseEntity<RodDto> createRod(@Valid @RequestBody CreateRodRequest request, @RequestParam UUID ownerId) {
        RodDto created = rodService.createRod(request, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RodDto> getRodById(@PathVariable UUID id) {
        return ResponseEntity.ok(rodService.getRodById(id));
    }

    @GetMapping
    public ResponseEntity<List<RodDto>> getAllRods() {
        return ResponseEntity.ok(rodService.getAllRods());
    }

    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<RodDto>> getRodsByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(rodService.getRodsByOwnerId(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RodDto> updateRod(@PathVariable UUID id, @Valid @RequestBody UpdateRodRequest request) {
        return ResponseEntity.ok(rodService.updateRod(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RodDto> deleteRod(@PathVariable UUID id) {
        return ResponseEntity.ok(rodService.deleteRod(id));
    }
}