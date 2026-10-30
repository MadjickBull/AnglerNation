package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "User DTO for API v1")
public record UserDto(
    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
    @Schema(description = "Username", example = "john_doe") String userName,
    @Schema(description = "First name", example = "John") String firstName,
    @Schema(description = "Last name", example = "Doe") String lastName,
    @Schema(description = "Email address", example = "john@example.com") String email,
    @Schema(description = "Phone number", example = "+1-555-0123") String phoneNumber,
    @Schema(description = "Account creation timestamp", example = "2024-01-15T10:30:00Z") Instant createdAt
) {
}