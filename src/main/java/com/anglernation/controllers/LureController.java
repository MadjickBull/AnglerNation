package com.anglernation.controllers;

import com.anglernation.dtos.CreateLureRequest;
import com.anglernation.dtos.LureDto;
import com.anglernation.dtos.UpdateLureRequest;
import com.anglernation.services.LureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lures")
public class LureController {

    private final LureService lureService;

    public LureController(LureService lureService) {
        this.lureService = lureService;
    }

    @PostMapping
    public ResponseEntity<LureDto> createLure(@Valid @RequestBody CreateLureRequest request, @RequestParam UUID ownerId) {
        LureDto created = lureService.createLure(request, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LureDto> getLureById(@PathVariable UUID id) {
        return ResponseEntity.ok(lureService.getLureById(id));
    }

    @GetMapping
    public ResponseEntity<List<LureDto>> getAllLures() {
        return ResponseEntity.ok(lureService.getAllLures());
    }

    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<LureDto>> getLuresByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(lureService.getLuresByOwnerId(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LureDto> updateLure(@PathVariable UUID id, @Valid @RequestBody UpdateLureRequest request) {
        return ResponseEntity.ok(lureService.updateLure(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LureDto> deleteLure(@PathVariable UUID id) {
        return ResponseEntity.ok(lureService.deleteLure(id));
    }
}