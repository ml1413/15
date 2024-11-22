package com.my.a15.data.storage.Room

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MyTypeConvertorFinalList {
    @TypeConverter
    fun fromList(value: List<Int?>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    fun toList(value: String): List<Int?> {
        return Json.decodeFromString(string = value)
    }
}