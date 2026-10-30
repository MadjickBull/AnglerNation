@Dao
interface LineDao {
    @Query("SELECT * FROM lines ORDER BY created_at DESC")
    suspend fun getAll(): List<RoomLine>

    @Query("SELECT * FROM lines WHERE id = :id")
    suspend fun getById(id: String): RoomLine?

    @Insert
    suspend fun insert(all: RoomLine...)

    @Insert
    suspend fun insertAll(vararg lines: RoomLine)

    @Query("DELETE FROM lines")
    suspend fun clearAll()
}