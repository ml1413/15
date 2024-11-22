package com.my.a15.data.storage.Room

import android.util.Log
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG= "MyTypeConvertorFinalList"
class MyTypeConvertorFinalList {
    @TypeConverter
    fun fromList(value: List<Int?>): String {
        val s = Json.encodeToString(value = value)
        Log.i(TAG, "fromList:s $s ")
        return s
    }

    @TypeConverter
    fun toList(value: String): List<Int?> {
        val l: List<Int?> = Json.decodeFromString(string = value)
        Log.i(TAG, "toList: l $l")
        return l
    }
}