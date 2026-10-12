package com.anglernation.dtos;

import java.time.Instant;
import java.util.UUID;

public record CommentDto(
        UUID id,
        String content,
        UUID authorId,
        UUID postId,
        Instant createdAt
) {
}