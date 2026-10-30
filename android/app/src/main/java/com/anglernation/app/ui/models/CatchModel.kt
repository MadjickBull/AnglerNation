@JsonClass(generateAdapter = true)
data class CatchDto(
    @Json("id") val id: UUID,
    @Json("location_id") val locationId: UUID,
    @Json("rod_id") val rodId: UUID,
    @Json("lure_id") val lureId: UUID,
    @Json("line_id") val lineId: UUID,
    @Json("fish_id") val fishId: UUID,
    @Json("angler_id") val anglerId: UUID,
    @Json("created_at") val createdAt: String
)

@JsonClass(generateAdapter = true)
data class CreateCatchRequest(
    @Json("location_id") @NotNull val locationId: UUID,
    @Json("rod_id") @Nullable val rodId: UUID?,
    @Json("lure_id") @Nullable val lureId: UUID?,
    @Json("line_id") @Nullable val lineId: UUID?,
    @Json("fish_id") @NotNull val fishId: UUID
)

@JsonClass(generateAdapter = true)
data class UpdateCatchRequest(
    @Json("location_id") @Nullable val locationId: UUID?,
    @Json("rod_id") @Nullable val rodId: UUID?,
    @Json("lure_id") @Nullable val lureId: UUID?,
    @Json("line_id") @Nullable val lineId: UUID?,
    @Json("fish_id") @Nullable val fishId: UUID?
)