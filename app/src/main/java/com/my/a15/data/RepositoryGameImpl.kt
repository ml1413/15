package com.my.a15.data

import com.my.a15.domain.MyModelNum
import com.my.a15.domain.RepositoryGame

class RepositoryGameImpl() : RepositoryGame {
    // todo neeed inject
    private val fifteenGame = FifteenGameImpl()
    override fun getStartGameModel(): MyModelNum {
        return fifteenGame.getStartGameModel()
    }

    override fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum {
        return fifteenGame.replaceElement(myModelNum = myModelNum, indexItem = indexItem, indexNull = indexNull)
    }
}