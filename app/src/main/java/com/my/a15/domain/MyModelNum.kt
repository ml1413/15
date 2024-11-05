package com.my.a15.domain

data class MyModelNum(
    val isVictory: Boolean = false,
    val countStep: Int = 0,
    val sqrt:Int,
    val listCells: List<MyCell?>
)

data class MyCell(
    val num: Int,
    val colorCell: ColorCell
)

enum class ColorCell {
    DEFAULT, CORRECT_POSITION
}
