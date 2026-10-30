@Dao
interface CatchDao {
    @Query("SELECT * FROM catches ORDER BY caught_at DESC")
    suspend fun getAll(): List<RoomCatch>

    @Query("SELECT * FROM catches WHERE rod_id = :rodId")
    suspend fun getByRodId(rodId: String): List<RoomCatch>

    @Query("SELECT * FROM catches WHERE fish_id = :fishId")
    suspend fun getByFishId(fishId: String): List<RoomCatch>

    @Insert
    suspend fun insert(all: RoomCatch...)

    @Insert
    suspend fun insertAll(vararg catches: RoomCatch)

    @Query("DELETE FROM catches")
    suspend fun clearAll()
}