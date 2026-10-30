@Dao
interface FishDao {
    @Query("SELECT * FROM fish ORDER BY created_at DESC")
    suspend fun getAll(): List<RoomFish>

    @Query("SELECT * FROM fish WHERE id = :id")
    suspend fun getById(id: String): RoomFish?

    @Insert
    suspend fun insert(all: RoomFish...)

    @Insert
    suspend fun insertAll(vararg fishes: RoomFish)

    @Query("DELETE FROM fish")
    suspend fun clearAll()
}