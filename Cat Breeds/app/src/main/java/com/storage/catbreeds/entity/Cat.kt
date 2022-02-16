package com.storage.catbreeds.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.storage.catbreeds.extension.BREED_COLUMN
import com.storage.catbreeds.extension.COAT_LENGTH_COLUMN
import com.storage.catbreeds.extension.COUNTRY_COLUMN
import com.storage.catbreeds.extension.ROW_ID_COLUMN
import com.storage.catbreeds.extension.SIZE_COLUMN
import com.storage.catbreeds.extension.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class Cat(
    @NonNull @ColumnInfo(name = BREED_COLUMN) val breed: String,
    @NonNull @ColumnInfo(name = COUNTRY_COLUMN) val country: String,
    @NonNull @ColumnInfo(name = COAT_LENGTH_COLUMN) val coatLength: CoatLength,
    @NonNull @ColumnInfo(name = SIZE_COLUMN) val size: CatSize,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ROW_ID_COLUMN) val id: Int = 0
) : Parcelable
