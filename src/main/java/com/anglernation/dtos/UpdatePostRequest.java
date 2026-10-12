package com.anglernation.dtos;

import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @Size(max = 100) String title,
        @Size(max = 2000) String description
) {
}