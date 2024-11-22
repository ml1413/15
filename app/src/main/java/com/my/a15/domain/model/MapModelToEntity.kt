package com.my.a15.domain.model

import com.my.a15.data.storage.Room.ColorCellEntity
import com.my.a15.data.storage.Room.ModelNumEntity
import com.my.a15.data.storage.Room.MyCellEntity
import com.my.a15.data.storage.Room.VariantGridEntity


fun MyModelNum.mapToEntity(): ModelNumEntity {
    return ModelNumEntity(
        isVictory = isVictory,
        variantGridEntity = variantGrid.mapToEntity(),
        finalListEntity = finalList,
        countStep = countStep,
        sqrt = sqrt,
        listCellsEntity = listCells.map { myCell -> myCell?.mapToEntity() }
    )
}

private fun MyCell.mapToEntity(): MyCellEntity {
    return MyCellEntity(
        num = num,
        colorCellEntity = colorCell.mapToEntity()
    )
}

private fun ColorCell.mapToEntity(): ColorCellEntity {
    return when (this) {
        ColorCell.DEFAULT -> ColorCellEntity.DEFAULT
        ColorCell.CORRECT_POSITION -> ColorCellEntity.CORRECT_POSITION
    }
}

private fun VariantGrid.mapToEntity(): VariantGridEntity {
    return when (this) {
        VariantGrid.GRID_4X4 -> VariantGridEntity.GRID_4X4
        VariantGrid.GRID_5X5 -> VariantGridEntity.GRID_5X5
        VariantGrid.GRID_6X6 -> VariantGridEntity.GRID_6X6
    }
}