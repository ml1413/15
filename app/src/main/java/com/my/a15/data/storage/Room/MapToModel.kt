package com.my.a15.data.storage.Room

import com.my.a15.domain.model.ColorCell
import com.my.a15.domain.model.MyCell
import com.my.a15.domain.model.MyModelNum

fun ModelNumEntity.mapToModel(): MyModelNum {
    return MyModelNum(
        isVictory = isVictory,
        countStep = countStep,
        sqrt = sqrt,
        listCells = listCellsEntity.map { myCellEntity -> myCellEntity?.mapToModel() }
    )
}

fun MyCellEntity.mapToModel(): MyCell {
    return MyCell(
        num = num,
        colorCell = colorCellEntity.mapToModel()
    )
}

fun ColorCellEntity.mapToModel(): ColorCell {
    return when (this) {
        ColorCellEntity.DEFAULT -> ColorCell.DEFAULT
        ColorCellEntity.CORRECT_POSITION -> ColorCell.CORRECT_POSITION
    }
}