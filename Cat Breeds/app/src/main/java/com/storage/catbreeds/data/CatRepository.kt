package com.storage.catbreeds.data

import androidx.annotation.WorkerThread
import androidx.sqlite.db.SimpleSQLiteQuery
import com.storage.catbreeds.entity.Cat
import com.storage.catbreeds.extension.BREED_COLUMN
import com.storage.catbreeds.extension.COAT_LENGTH_COLUMN
import com.storage.catbreeds.extension.COUNTRY_COLUMN
import com.storage.catbreeds.extension.SIZE_COLUMN
import com.storage.catbreeds.extension.TABLE_NAME
import kotlinx.coroutines.flow.Flow

class CatRepository(
    private val database: CatDbHelper,
    private val catDao: CatDao,
) {
    private val dao: CatDao
        get() = if (useRoom) catDao else database

    // these fields will be configured from SharedPreferences by MainFragment
    var useRoom = false
    var sortColumn: String? = null
        set(column) {
            val columnsList =
                listOf(BREED_COLUMN, SIZE_COLUMN, COAT_LENGTH_COLUMN, COUNTRY_COLUMN)
            field = if (column in columnsList) column else null
        }

    val allCats: Flow<List<Cat>>
        get() =
            if (sortColumn != null) {
                dao.getCatsByQuery(
                    SimpleSQLiteQuery(
                        "SELECT * FROM $TABLE_NAME ORDER BY $sortColumn COLLATE NOCASE ASC"
                    )
                )
            } else
                dao.getAllCatsInRawOrder()

    @WorkerThread
    suspend fun insert(cat: Cat) {
        dao.insert(cat)
    }

    suspend fun update(cat: Cat) {
        dao.update(cat)
    }

    suspend fun delete(cat: Cat) {
        dao.delete(cat)
    }
}
