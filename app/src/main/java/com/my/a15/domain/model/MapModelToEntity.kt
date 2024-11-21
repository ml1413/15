package com.my.a15.domain.model

import com.my.a15.data.storage.Room.ColorCellEntity
import com.my.a15.data.storage.Room.ModelNumEntity
import com.my.a15.data.storage.Room.MyCellEntity


fun MyModelNum.mapToEntity(): ModelNumEntity {
    return ModelNumEntity(
        isVictory = isVictory,
        countStep = countStep,
        sqrt = sqrt,
        listCellsEntity = listCells.map { myCell -> myCell?.mapToEntity() }
    )
}

fun MyCell.mapToEntity(): MyCellEntity {
    return MyCellEntity(
        num = num,
        colorCellEntity = colorCell.mapToEntity()
    )
}

fun ColorCell.mapToEntity(): ColorCellEntity {
    return when (this) {
        ColorCell.DEFAULT -> ColorCellEntity.DEFAULT
        ColorCell.CORRECT_POSITION -> ColorCellEntity.CORRECT_POSITION
    }
}