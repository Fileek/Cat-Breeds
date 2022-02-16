package com.storage.catbreeds.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.storage.catbreeds.entity.Cat
import com.storage.catbreeds.extension.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllCatsInRawOrder(): Flow<List<Cat>>

    @RawQuery(observedEntities = [Cat::class])
    fun getCatsByQuery(query: SupportSQLiteQuery): Flow<List<Cat>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cat: Cat)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(cat: Cat)

    @Delete
    suspend fun delete(cat: Cat)
}
