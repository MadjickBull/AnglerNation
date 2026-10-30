
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Update Post Request for API v1")
public record UpdatePostRequest(
        @Schema(description = "Title", example = "Updated Fishing Trip") @Size(max = 100) String title,
        @Schema(description = "Description", example = "Updated description of the fishing trip") @Size(max = 2000) String description
) {
}