@Entity(tableName = "rods")
data class RoomRod(
    @PrimaryKey @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "length") val length: Double,
    @Json(name = "weight") val weight: Double,
    @Json(name = "rod_type") val rodType: String,
    @Json(name = "rod_power") val rodPower: String,
    @Json(name = "rod_action") val rodAction: String,
    @Json(name = "owner_id") val ownerId: String,
    @Json(name = "created_at") val createdAt: Long,
    @Json(name = "updated_at") val updatedAt: Long
)