package com.my.a15.data

import com.my.a15.domain.MyModelNum
import com.my.a15.domain.RepositoryGame
import javax.inject.Inject

class RepositoryGameImpl@Inject constructor( private val fifteenGame: FifteenGame) : RepositoryGame {

    override fun getStartGameModel(grid: VariantGrid): MyModelNum {
        return fifteenGame.getStartGameModel(grid = grid)
    }

    override fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum {
        return fifteenGame.replaceElement(myModelNum = myModelNum, indexItem = indexItem, indexNull = indexNull)
    }
}