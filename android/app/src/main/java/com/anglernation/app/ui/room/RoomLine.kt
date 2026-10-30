@Entity(tableName = "lines")
data class RoomLine(
    @PrimaryKey @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String,
    @Json(name = "test_strength") val testStrength: Double
)