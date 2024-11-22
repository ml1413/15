package com.my.a15.data.game


import com.my.a15.domain.model.ColorCell
import com.my.a15.domain.model.MyCell
import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid
import kotlin.math.sqrt


class FifteenGameImpl : FifteenGame {
    override fun getStartGameModel(): MyModelNum {
        return getStartModel()
    }

    override fun replaceElement(
        myModelNum: MyModelNum,
        indexItem: Int,
        indexNull: Int
    ): MyModelNum {
        val oldList = myModelNum.listCells
        val finalList = myModelNum.finalList
        val newList = swap(finalList, oldList, indexItem, indexNull)
        val isVictory = newList.map { it?.num } == finalList
        return myModelNum.copy(
            isVictory = isVictory,
            countStep = myModelNum.countStep + 1,
            listCells = newList
        )
    }

    /** OTHER METHOD ____________________________________________________________________________*/
    // todo done
    private fun getStartModel(): MyModelNum {
        return MyModelNum().let { model ->
            var indexNull: Int? = null
            val finalList = (1..model.variantGrid.count) + null
            model.copy(
                finalList = finalList,
                sqrt = sqrt(finalList.size.toDouble()).toInt(),
                listCells = getListInt(donorList = finalList).mapIndexed { index, intValue ->
                    if (indexNull == null && intValue == null) {
                        indexNull = index
                    }
                    intValue?.let {
                        val color =
                            if (intValue == finalList[index]) ColorCell.CORRECT_POSITION else ColorCell.DEFAULT
                        MyCell(num = it, colorCell = color)
                    }
                }
            )
        }
    }


    private fun getColorCorrectPosition(
        finalList: List<Int?>,
        intValue: Int,
        index: Int
    ): ColorCell {
        return if (intValue == finalList[index]) ColorCell.CORRECT_POSITION
        else ColorCell.DEFAULT

    }

    private fun getListInt(donorList: List<Int?>): List<Int?> {
        val mutableList = donorList.toMutableList()

        // два дня на коленях умолял gpt сделать код
        fun isSolvable(tiles: List<Int?>): Boolean {
            val flattened = tiles.filterNotNull() // Убираем null
            val inversions = flattened.indices.sumBy { i ->
                (i + 1 until flattened.size).count { j -> flattened[i] > flattened[j] }
            }
            // Находим позицию пустой клетки (null)
            val gridSize =
                Math.sqrt(tiles.size.toDouble()).toInt() // Размер сетки (например, 4x4)
            val nullRow = tiles.indexOf(null) / gridSize // Ряд, где находится null

            // Для четного размера поля нужно учитывать как число инверсий, так и позицию пустой клетки
            return if (gridSize % 2 == 1) {
                inversions % 2 == 0 // Если размер сетки нечётный, конфигурация решаема, если инверсий чётное число
            } else {
                (inversions + nullRow) % 2 == 1 // Для чётного размера сетки решаемость зависит от суммы инверсий и позиции null
            }
        }
        do {
            mutableList.shuffle() // Перемешиваем список
        } while (!isSolvable(mutableList)) // Проверяем, решаемая ли конфигурация
        //_____________________________________________________________________________________
        return mutableList
    }

    private fun swap(
        finalList: List<Int?>,
        oldList: List<MyCell?>,
        indexItem: Int,
        indexNull: Int
    ): List<MyCell?> {
        val newList = oldList.toMutableList()
        newList[indexItem] = newList[indexNull].also { newList[indexNull] = newList[indexItem] }
        newList[indexNull] = newList[indexNull]?.let { myCell ->
            val color =
                getColorCorrectPosition(
                    finalList = finalList,
                    intValue = myCell.num,
                    index = indexNull
                )
            myCell.copy(colorCell = color)
        }

        return newList
    }
}