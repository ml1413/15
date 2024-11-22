package com.my.a15.data.storage.Room

import android.util.Log
import com.my.a15.data.storage.Storage
import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.mapToEntity
import javax.inject.Inject

private const val TAG = "StorageImpl"

class StorageImpl @Inject constructor(private val db: FifteenDB) : Storage {
    override suspend fun saveToStorage(modelNum: MyModelNum) {
        val modelNumEntity = modelNum.mapToEntity()
        Log.i(TAG, "saveToStorage: modelNum $modelNum")
        Log.i(TAG, "saveToStorage: modelNumEntity $modelNumEntity")
        db.getDao().updateModel(modelNumEntity = modelNumEntity)
    }

    override suspend fun getFromStorage(): MyModelNum? {
        val modelNumEntity = db.getDao().getModelFromDB()
        Log.i(TAG, "getFromStorage: modelNumEntity $modelNumEntity")
        val myModelNum = modelNumEntity?.mapToModel()
        Log.i(TAG, "getFromStorage: myModelNum $myModelNum")
        return myModelNum
    }
}