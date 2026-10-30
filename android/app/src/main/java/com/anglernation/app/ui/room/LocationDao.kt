@Dao
interface LocationDao {
    @Query("SELECT * FROM locations ORDER BY created_at DESC")
    suspend fun getAll(): List<RoomLocation>

    @Query("SELECT * FROM locations WHERE id = :id")
    suspend fun getById(id: String): RoomLocation?

    @Insert
    suspend fun insert(all: RoomLocation...)

    @Insert
    suspend fun insertAll(vararg locations: RoomLocation)

    @Query("DELETE FROM locations")
    suspend fun clearAll()
}