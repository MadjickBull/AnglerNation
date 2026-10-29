
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Create Post Request for API v1")
public record CreatePostRequest(
        @Schema(description = "Title", example = "My Fishing Trip") @NotBlank @Size(max = 100) String title,
        @Schema(description = "Description", example = "Went fishing and caught a big fish!") @Size(max = 2000) String description
) {
}