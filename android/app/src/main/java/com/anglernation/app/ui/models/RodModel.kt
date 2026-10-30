@JsonClass(generateAdapter = true)
data class RodDto(
    @Json("id") val id: UUID,
    @Json("rod_type") val rodType: RodType,
    @Json("name") val name: String,
    @Json("length") val length: Double,
    @Json("weight") val weight: Double,
    @Json("rod_power") val rodPower: RodPower,
    @Json("rod_action") val rodAction: RodAction,
    @Json("owner_id") val ownerId: UUID,
    @Json("created_at") val createdAt: String,
    @Json("updated_at") val updatedAt: String
)

@JsonClass(generateAdapter = true)
data class CreateRodRequest(
    @Json("rod_type") @NotNull val rodType: RodType,
    @Json("name") @NotBlank val name: String,
    @Json("length") @Positive val length: Double,
    @Json("weight") @Positive val weight: Double,
    @Json("rod_power") val rodPower: RodPower,
    @Json("rod_action") val rodAction: RodAction,
    @Json("owner_id") @NotNull val ownerId: UUID
)

@JsonClass(generateAdapter = true)
data class UpdateRodRequest(
    @Json("rod_type") val rodType: RodType?,
    @Json("name") @Nullable val name: String?,
    @Json("length") @Positive @Nullable val length: Double?,
    @Json("weight") @Positive @Nullable val weight: Double?,
    @Json("rod_power") val rodPower: RodPower?,
    @Json("rod_action") val rodAction: RodAction?
)