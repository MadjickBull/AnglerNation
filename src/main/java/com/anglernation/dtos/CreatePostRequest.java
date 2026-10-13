package com.anglernation.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank @Size(max = 100) String title,
        @Size(max = 2000) String description
) {
}