@Dao
interface CommentDao {
    @Query("SELECT * FROM comments ORDER BY created_at DESC")
    suspend fun getAll(): List<RoomComment>

    @Query("SELECT * FROM comments WHERE post_id = :postId")
    suspend fun getByPostId(postId: String): List<RoomComment>

    @Query("SELECT * FROM comments WHERE id = :id")
    suspend fun getById(id: String): RoomComment?

    @Insert
    suspend fun insert(all: RoomComment...)

    @Insert
    suspend fun insertAll(vararg comments: RoomComment)

    @Query("DELETE FROM comments")
    suspend fun clearAll()
}