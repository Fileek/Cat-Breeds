package com.storage.catbreeds.main

import android.app.Application
import com.storage.catbreeds.data.CatDbHelper
import com.storage.catbreeds.data.CatRepository
import com.storage.catbreeds.data.CatRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CatsApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { CatDbHelper(applicationContext) }
    private val roomDatabase by lazy { CatRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { CatRepository(database, roomDatabase.catDao()) }
}
