package com.my.a15.domain

import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid

interface RepositoryGame {
    fun getStartGameModel(): MyModelNum
    fun getRestartStartGameModel(variantGrid: VariantGrid): MyModelNum
    fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum
    suspend fun saveToStorage(myModelNum: MyModelNum)
    suspend fun getFromStorage(): MyModelNum?
}