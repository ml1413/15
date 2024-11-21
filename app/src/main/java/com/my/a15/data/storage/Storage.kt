package com.my.a15.data.storage

import com.my.a15.domain.model.MyModelNum

interface Storage {
    suspend fun saveToStorage(modelNum: MyModelNum)
    suspend fun getFromStorage(): MyModelNum?
}