@Entity(tableName = "lures")
data class RoomLure(
    @PrimaryKey @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String,
    @Json(name = "length") val length: Double,
    @Json(name = "weight") val weight: Double,
    @Json(name = "buoyancy") val buoyancy: String
)