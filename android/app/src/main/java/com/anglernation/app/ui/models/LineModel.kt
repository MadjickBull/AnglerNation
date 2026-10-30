@JsonClass(generateAdapter = true)
data class LineDto(
    @Json("id") val id: UUID,
    @Json("line_type") val lineType: LineType,
    @Json("name") val name: String,
    @Json("diameter") val diameter: Double,
    @Json("breaking_strength") val breakingStrength: Int,
    @Json("color") val color: String,
    @Json("owner_id") val ownerId: UUID
)

@JsonClass(generateAdapter = true)
data class CreateLineRequest(
    @Json("line_type") @NotNull val lineType: LineType,
    @Json("name") @NotBlank val name: String,
    @Json("diameter") @Positive val diameter: Double,
    @Json("breaking_strength") @Positive val breakingStrength: Int,
    @Json("color") @NotBlank val color: String
)

@JsonClass(generateAdapter = true)
data class UpdateLineRequest(
    @Json("line_type") val lineType: LineType?,
    @Json("name") @Nullable val name: String?,
    @Json("diameter") @Positive @Nullable val diameter: Double?,
    @Json("breaking_strength") @Positive @Nullable val breakingStrength: Int?,
    @Json("color") @Nullable val color: String?
)