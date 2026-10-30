@Entity(tableName = "comments")
data class RoomComment(
    @PrimaryKey @Json(name = "id") val id: String,
    @Json(name = "content") val content: String,
    @Json(name = "post_id") val postId: String,
    @Json(name = "created_at") val createdAt: Long
)