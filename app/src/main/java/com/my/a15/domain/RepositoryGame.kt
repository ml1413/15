package com.my.a15.domain

interface RepositoryGame {
    fun getStartGameModel():MyModelNum
    fun replaceElement(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum

}