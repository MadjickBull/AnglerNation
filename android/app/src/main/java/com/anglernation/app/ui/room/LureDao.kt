@Dao
interface LureDao {
    @Query("SELECT * FROM lures ORDER BY created_at DESC")
    suspend fun getAll(): List<RoomLure>

    @Query("SELECT * FROM lures WHERE id = :id")
    suspend fun getById(id: String): RoomLure?

    @Insert
    suspend fun insert(all: RoomLure...)

    @Insert
    suspend fun insertAll(vararg lures: RoomLure)

    @Query("DELETE FROM lures")
    suspend fun clearAll()
}