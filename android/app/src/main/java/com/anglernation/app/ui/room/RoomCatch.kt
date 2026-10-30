@Entity(tableName = "catches")
data class RoomCatch(
    @PrimaryKey @Json(name = "id") val id: String,
    @Json(name = "rod_id") val rodId: String,
    @Json(name = "fish_id") val fishId: String,
    @Json(name = "caught_at") val caughtAt: Long
)