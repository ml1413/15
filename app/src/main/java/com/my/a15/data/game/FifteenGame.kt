package com.my.a15.data.game

import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid

interface FifteenGame {
    fun getStartGameModel(): MyModelNum
    fun getRestartStartGameModel(variantGrid: VariantGrid): MyModelNum
    fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum
}