
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Create Comment Request for API v1")
public record CreateCommentRequest(
        @Schema(description = "Comment content", example = "Great catch!") @NotBlank @Size(max = 1000) String content
) {
}