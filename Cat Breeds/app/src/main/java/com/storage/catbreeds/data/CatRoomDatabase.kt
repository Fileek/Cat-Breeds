package com.storage.catbreeds.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.storage.catbreeds.entity.Cat
import com.storage.catbreeds.extension.TABLE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class CatRoomDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

    private class CatDatabaseCallback(
        private val scope: CoroutineScope,
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    CatsList.allCats.forEach {
                        database.catDao().insert(it)
                    }
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CatRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
        ): CatRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatRoomDatabase::class.java,
                    TABLE_NAME
                )
                    .addCallback(CatDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
