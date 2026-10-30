@Dao
interface RodDao {
    @Query("SELECT * FROM rods ORDER BY created_at DESC")
    suspend fun getAll(): List<RoomRod>

    @Query("SELECT * FROM rods WHERE id = :id")
    suspend fun getById(id: String): RoomRod?

    @Insert
    suspend fun insert(all: RoomRod...)

    @Insert
    suspend fun insertAll(vararg rods: RoomRod)

    @Delete
    suspend fun delete(rod: RoomRod)

    @Query("DELETE FROM rods")
    suspend fun clearAll()
}