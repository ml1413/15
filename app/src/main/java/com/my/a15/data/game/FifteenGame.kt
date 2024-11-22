package com.my.a15.data.game

import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid

interface FifteenGame {
    fun getStartGameModel(): MyModelNum
    fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum
}