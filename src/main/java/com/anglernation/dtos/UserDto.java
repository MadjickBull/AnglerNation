package com.anglernation.dtos;

import java.time.Instant;
import java.util.UUID;

public record UserDto(
        UUID id,
        String userName,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Instant createdAt
) {
}