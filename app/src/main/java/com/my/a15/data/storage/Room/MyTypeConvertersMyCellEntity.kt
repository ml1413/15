package com.my.a15.data.storage.Room

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MyTypeConvertersMyCellEntity {
    @TypeConverter
    fun fromModelList(value: List<MyCellEntity?>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    fun toModelList(value: String): List<MyCellEntity?> {
        return Json.decodeFromString(value)
    }
}