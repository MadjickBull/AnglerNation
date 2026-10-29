
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Update Comment Request for API v1")
public record UpdateCommentRequest(
        @Schema(description = "Comment content", example = "Updated comment") @Size(max = 1000) String content
) {
}