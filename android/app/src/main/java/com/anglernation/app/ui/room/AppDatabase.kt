@Database(entities = [
    RoomRod::class,
    RoomLure::class,
    RoomLine::class,
    RoomLocation::class,
    RoomFish::class,
    RoomCatch::class,
    RoomPost::class,
    RoomComment::class
], version = 1, exportSchemas = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rodDao(): RodDao
    abstract fun lureDao(): LureDao
    abstract fun lineDao(): LineDao
    abstract fun locationDao(): LocationDao
    abstract fun fishDao(): FishDao
    abstract fun catchDao(): CatchDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "angler_nation_db"
                ).fallbackToDestructiveMigration().build()
            }
        }
    }
}