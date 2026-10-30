@Entity(tableName = "locations")
data class RoomLocation(
    @PrimaryKey @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "visibility") val visibility: String
)