package com.anglernation.controllers;

import com.anglernation.dtos.CatchDto;
import com.anglernation.dtos.CreateCatchRequest;
import com.anglernation.dtos.UpdateCatchRequest;
import com.anglernation.services.CatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/catches")
public class CatchController {

    private final CatchService catchService;

    public CatchController(CatchService catchService) {
        this.catchService = catchService;
    }

    @PostMapping
    public ResponseEntity<CatchDto> createCatch(@Valid @RequestBody CreateCatchRequest request, @RequestParam UUID anglerId) {
        CatchDto created = catchService.createCatch(request, anglerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatchDto> getCatchById(@PathVariable UUID id) {
        return ResponseEntity.ok(catchService.getCatchById(id));
    }

    @GetMapping
    public ResponseEntity<List<CatchDto>> getAllCatches() {
        return ResponseEntity.ok(catchService.getAllCatches());
    }

    @GetMapping("/angler/{anglerId}")
    public ResponseEntity<List<CatchDto>> getCatchesByAngler(@PathVariable UUID anglerId) {
        return ResponseEntity.ok(catchService.getCatchesByAnglerId(anglerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatchDto> updateCatch(@PathVariable UUID id, @Valid @RequestBody UpdateCatchRequest request) {
        return ResponseEntity.ok(catchService.updateCatch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CatchDto> deleteCatch(@PathVariable UUID id) {
        return ResponseEntity.ok(catchService.deleteCatch(id));
    }
}