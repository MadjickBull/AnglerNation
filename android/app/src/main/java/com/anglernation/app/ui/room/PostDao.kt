@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY created_at DESC")
    suspend fun getAll(): List<RoomPost>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getById(id: String): RoomPost?

    @Insert
    suspend fun insert(all: RoomPost...)

    @Insert
    suspend fun insertAll(vararg posts: RoomPost)

    @Query("DELETE FROM posts")
    suspend fun clearAll()
}