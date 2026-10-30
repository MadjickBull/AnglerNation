@JsonClass(generateAdapter = true)
data class LureDto(
    @Json("id") val id: UUID,
    @Json("lure_type") val lureType: LureType,
    @Json("name") val name: String,
    @Json("color") val color: String,
    @Json("size") val size: Double,
    @Json("weight") val weight: Double,
    @Json("lure_buoyancy") val lureBuoyancy: LureBuoyancy,
    @Json("owner_id") val ownerId: UUID
)

@JsonClass(generateAdapter = true)
data class CreateLureRequest(
    @Json("lure_type") @NotNull val lureType: LureType,
    @Json("name") @NotBlank val name: String,
    @Json("color") @NotBlank val color: String,
    @Json("size") @Positive val size: Double,
    @Json("weight") @PositiveOrZero val weight: Double,
    @Json("lure_buoyancy") @NotNull val lureBuoyancy: LureBuoyancy
)

@JsonClass(generateAdapter = true)
data class UpdateLureRequest(
    @Json("lure_type") val lureType: LureType?,
    @Json("name") @Nullable val name: String?,
    @Json("color") @Nullable val color: String?,
    @Json("size") @Positive @Nullable val size: Double?,
    @Json("weight") @PositiveOrZero @Nullable val weight: Double?,
    @Json("lure_buoyancy") val lureBuoyancy: LureBuoyancy?
)