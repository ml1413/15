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
        Log.i(TAG, "saveToStorage: ")
        db.getDao().updateModel(modelNumEntity = modelNumEntity)
    }

    override suspend fun getFromStorage(): MyModelNum? {
        val modelNumEntity = db.getDao().getModelFromDB()
        val myModelNum = modelNumEntity?.mapToModel()
        return  myModelNum
    }
}