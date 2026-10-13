package com.anglernation.dtos;

import jakarta.validation.constraints.Size;

public record UpdateCommentRequest(
        @Size(max = 1000) String content
) {
}