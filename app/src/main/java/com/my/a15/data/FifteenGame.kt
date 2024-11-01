package com.my.a15.data

import com.my.a15.domain.MyModelNum

interface FifteenGame {
    fun getStartGameModel(): MyModelNum
    fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum
}