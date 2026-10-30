@JsonClass(generateAdapter = true)
data class CommentDto(
    @Json("id") val id: UUID,
    @Json("content") val content: String,
    @Json("author_id") val authorId: UUID,
    @Json("post_id") val postId: UUID,
    @Json("created_at") val createdAt: String
)

@JsonClass(generateAdapter = true)
data class CreateCommentRequest(
    @Json("content") @NotBlank @Size(max = 1000) val content: String
)

@JsonClass(generateAdapter = true)
data class UpdateCommentRequest(
    @Json("content") @Size(max = 1000) @Nullable val content: String?
)