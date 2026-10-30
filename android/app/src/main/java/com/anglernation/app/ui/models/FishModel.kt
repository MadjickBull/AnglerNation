@JsonClass(generateAdapter = true)
data class FishDto(
    @Json("id") val id: UUID,
    @Json("fish_type") val fishType: FishType,
    @Json("name") val name: String,
    @Json("weight") val weight: Double,
    @Json("size") val size: Double
)

@JsonClass(generateAdapter = true)
data class CreateFishRequest(
    @Json("fish_type") @NotNull val fishType: FishType,
    @Json("name") @NotBlank val name: String,
    @Json("weight") @Positive val weight: Double,
    @Json("size") @PositiveOrZero val size: Double
)

@JsonClass(generateAdapter = true)
data class UpdateFishRequest(
    @Json("fish_type") val fishType: FishType?,
    @Json("name") @Nullable val name: String?,
    @Json("weight") @Positive @Nullable val weight: Double?,
    @Json("size") @PositiveOrZero @Nullable val size: Double?
)