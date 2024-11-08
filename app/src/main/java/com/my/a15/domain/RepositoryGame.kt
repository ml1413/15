package com.my.a15.domain

import com.my.a15.data.VariantGrid

interface RepositoryGame {
    fun getStartGameModel(grid: VariantGrid):MyModelNum
    fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum

}