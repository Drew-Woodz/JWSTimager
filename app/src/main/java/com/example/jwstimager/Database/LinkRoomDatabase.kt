package com.example.jwstimager.Database

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Link::class), version = 1, exportSchema = false)
public abstract class LinkRoomDatabase : RoomDatabase() {

    abstract fun linkDao(): LinkDao

    private class LinkDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var linkDao = database.linkDao()

                    // Delete all content here.
                    linkDao.deleteAll()

                    // Add sample links
                    var link = Link(1,"https://stsci-opo.org/STScI-01GEPRPYZKGEY9GT152DEK45GC.png")
                    linkDao.insert(link)
                    link = Link(2,"https://stsci-opo.org/STScI-01GE0993DBCZXEBR2ZH5JAHTT6.png")
                    linkDao.insert(link)

                    // Add remaining links
                    link = Link(3, "TODO!")
                    linkDao.insert(link)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening
        // at the same time.
        @Volatile
        private var INSTANCE: LinkRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LinkRoomDatabase {
            // if the INSTANCE is not null, the return it.
            // if it is, then create the database.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LinkRoomDatabase::class.java,
                    "link_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}