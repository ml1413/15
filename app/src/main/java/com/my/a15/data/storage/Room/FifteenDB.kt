package com.my.a15.data.storage.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ModelNumEntity::class], version = 1)
@TypeConverters(value = [MyTypeConvertersMyCellEntity::class, MyTypeConvertorFinalList::class])
abstract class FifteenDB : RoomDatabase() {
    abstract fun getDao(): FifteenDao

    companion object {
        const val NAME_DB = "name_db"
    }
}