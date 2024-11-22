package com.my.a15.domain.model

data class MyModelNum(
    val isVictory: Boolean = false,
    val variantGrid: VariantGrid = VariantGrid.GRID_4X4,
    val finalList: List<Int?> = emptyList(),
    val countStep: Int = 0,
    val sqrt: Int = 0,
    val listCells: List<MyCell?> = emptyList()
)

data class MyCell(
    val num: Int,
    val colorCell: ColorCell
)

enum class ColorCell {
    DEFAULT, CORRECT_POSITION
}

enum class VariantGrid(val count: Int) {
    GRID_4X4(15),
    GRID_5X5(24),
    GRID_6X6(35)
}

