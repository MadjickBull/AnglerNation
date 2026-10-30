@Entity(tableName = "posts")
data class RoomPost(
    @PrimaryKey @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String?,
    @Json(name = "created_at") val createdAt: Long
)