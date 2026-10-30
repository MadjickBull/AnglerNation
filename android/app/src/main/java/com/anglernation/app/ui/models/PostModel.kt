@JsonClass(generateAdapter = true)
data class PostDto(
    @Json("id") val id: UUID,
    @Json("title") val title: String,
    @Json("description") val description: String?,
    @Json("author_id") val authorId: UUID,
    @Json("created_at") val createdAt: String,
    @Json("updated_at") val updatedAt: String
)

@JsonClass(generateAdapter = true)
data class CreatePostRequest(
    @Json("title") @NotBlank @Size(max = 100) val title: String,
    @Json("description") @Size(max = 2000) val description: String?
)

@JsonClass(generateAdapter = true)
data class UpdatePostRequest(
    @Json("title") @Size(max = 100) @Nullable val title: String?,
    @Json("description") @Size(max = 2000) @Nullable val description: String?
)