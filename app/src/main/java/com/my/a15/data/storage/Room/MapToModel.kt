package com.my.a15.data.storage.Room

import com.my.a15.domain.model.ColorCell
import com.my.a15.domain.model.MyCell
import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid

fun ModelNumEntity.mapToModel(): MyModelNum {
    return MyModelNum(
        isVictory = isVictory,
        variantGrid = variantGridEntity.mapToModel(),
        finalList = finalListEntity,
        countStep = countStep,
        sqrt = sqrt,
        listCells = listCellsEntity.map { myCellEntity -> myCellEntity?.mapToModel() }
    )
}


private fun MyCellEntity.mapToModel(): MyCell {
    return MyCell(
        num = num,
        colorCell = colorCellEntity.mapToModel()
    )
}

private fun ColorCellEntity.mapToModel(): ColorCell {
    return when (this) {
        ColorCellEntity.DEFAULT -> ColorCell.DEFAULT
        ColorCellEntity.CORRECT_POSITION -> ColorCell.CORRECT_POSITION
    }
}

private fun VariantGridEntity.mapToModel(): VariantGrid {
    return when (this) {
        VariantGridEntity.GRID_4X4 -> VariantGrid.GRID_4X4
        VariantGridEntity.GRID_5X5 -> VariantGrid.GRID_5X5
        VariantGridEntity.GRID_6X6 -> VariantGrid.GRID_6X6
    }
}