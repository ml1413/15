package com.my.a15.data.game

import com.my.a15.domain.model.MyModelNum

interface FifteenGame {
    fun getStartGameModel(grid: VariantGrid): MyModelNum
    fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum
}