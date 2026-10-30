@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json("id") val id: UUID,
    @Json("name") val name: String,
    @Json("visibility") val visibility: LocationVisibility,
    @Json("altitude") val altitude: Double?,
    @Json("longitude") val longitude: Double?,
    @Json("latitude") val latitude: Double?
)

@JsonClass(generateAdapter = true)
data class CreateLocationRequest(
    @Json("name") @NotBlank val name: String,
    @Json("visibility") @NotNull val visibility: LocationVisibility,
    @Json("altitude") @PositiveOrZero val altitude: Double?,
    @Json("longitude") val longitude: Double?,
    @Json("latitude") val latitude: Double?
)

@JsonClass(generateAdapter = true)
data class UpdateLocationRequest(
    @Json("name") @Nullable val name: String?,
    @Json("visibility") @Nullable val visibility: LocationVisibility?,
    @Json("altitude") @PositiveOrZero @Nullable val altitude: Double?,
    @Json("longitude") @Nullable val longitude: Double?,
    @Json("latitude") @Nullable val latitude: Double?
)