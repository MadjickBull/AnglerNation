package com.anglernation.controllers;

import com.anglernation.dtos.CreateFishRequest;
import com.anglernation.dtos.FishDto;
import com.anglernation.dtos.UpdateFishRequest;
import com.anglernation.services.FishService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fish")
public class FishController {

    private final FishService fishService;

    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @PostMapping
    public ResponseEntity<FishDto> createFish(@Valid @RequestBody CreateFishRequest request) {
        FishDto created = fishService.createFish(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FishDto> getFishById(@PathVariable UUID id) {
        return ResponseEntity.ok(fishService.getFishById(id));
    }

    @GetMapping
    public ResponseEntity<List<FishDto>> getAllFish() {
        return ResponseEntity.ok(fishService.getAllFish());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FishDto> updateFish(@PathVariable UUID id, @Valid @RequestBody UpdateFishRequest request) {
        return ResponseEntity.ok(fishService.updateFish(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FishDto> deleteFish(@PathVariable UUID id) {
        return ResponseEntity.ok(fishService.deleteFish(id));
    }
}