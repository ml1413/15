package com.my.a15.data.repository

import android.util.Log
import com.my.a15.data.game.FifteenGame
import com.my.a15.data.game.VariantGrid
import com.my.a15.data.storage.Storage
import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.model.MyModelNum
import javax.inject.Inject
private const val TAG = "RepositoryGameImpl"
class RepositoryGameImpl @Inject constructor(
    private val fifteenGame: FifteenGame,
    private val storage: Storage
) : RepositoryGame {

    override fun getStartGameModel(grid: VariantGrid): MyModelNum {
        return fifteenGame.getStartGameModel(grid = grid)
    }

    override fun replaceElement(
        myModelNum: MyModelNum,
        indexItem: Int,
        indexNull: Int
    ): MyModelNum {
        return fifteenGame.replaceElement(
            myModelNum = myModelNum,
            indexItem = indexItem,
            indexNull = indexNull
        )
    }

    override suspend fun saveToStorage(myModelNum: MyModelNum) {
        storage.saveToStorage(modelNum = myModelNum)
    }

    override suspend fun getFromStorage(): MyModelNum? {
        val myModelNum= storage.getFromStorage()
        return myModelNum
    }
}