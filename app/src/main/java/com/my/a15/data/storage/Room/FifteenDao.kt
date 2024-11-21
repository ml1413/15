package com.my.a15.data.storage.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.my.a15.data.storage.Room.ModelNumEntity.Companion.TABLE_NAME

@Dao
interface FifteenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateModel(modelNumEntity: ModelNumEntity)

    @Query("select * from $TABLE_NAME")
    suspend fun getModelFromDB(): ModelNumEntity?
}