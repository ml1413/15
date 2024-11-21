package com.my.a15.domain

import com.my.a15.data.game.VariantGrid
import com.my.a15.domain.model.MyModelNum

interface RepositoryGame {
    fun getStartGameModel(grid: VariantGrid): MyModelNum
    fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum
    suspend fun saveToStorage(myModelNum: MyModelNum)
    suspend fun getFromStorage(): MyModelNum?
}