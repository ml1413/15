package com.my.a15

interface FifteenEngine {
    fun transitionState(oldState: List<Int?>, cell: Int): List<Int?>
    fun isWin(state: List<Int?>): Boolean
    fun getInitialState(): List<Int?>

    companion object : FifteenEngine {
        const val EMPTY = 16
        const val DIM = 4
        val FINAL_STATE = (1..15) + null

        override fun transitionState(oldState: List<Int?>, cell: Int): List<Int?> {
            return replace(value = cell, list = oldState)
        }

        override fun isWin(state: List<Int?>): Boolean {
//            var temp = 0
//            var isSorted = false
//            for (i in state) {
//                if ((i != null && temp > i) || (temp < 15 && i == null)) {
//                    isSorted = true
//                    break
//                }
//                if (i != null) temp = i
//            }
//            if (!isSorted) println("ПОБЕДА")
//            return isSorted
            return state == FINAL_STATE
        }

        override fun getInitialState(): List<Int?> {
            val mutableList = FINAL_STATE.toMutableList()
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
            return mutableList.toList()
        }

        private fun checkPeripheryNum(indexNull: Int, indexCell: Int, list: List<Int?>): Boolean {
            val leftNum = if (indexNull % DIM == 0) null else indexNull - 1
            val rightNum = if ((indexNull + 1) % DIM == 0) null else indexNull + 1
            val upNum = if (indexNull < DIM) null else indexNull - DIM
            val bottomNum = if (indexNull + 4 > list.lastIndex) null else indexNull + DIM
            val listIndex = listOf(leftNum, rightNum, upNum, bottomNum)
            return listIndex.contains(indexCell)
        }

        private fun replace(value: Int, list: List<Int?>): List<Int?> {
            val indexNull = list.indexOf(null)
            val indexCell = list.indexOf(value)
            val cellIsInRange = value in (0..EMPTY)
            val isCorrectNum =
                checkPeripheryNum(indexNull = indexNull, indexCell = indexCell, list = list)
            return if (cellIsInRange && isCorrectNum) {
                val mutableList = list.toMutableList()
                mutableList[indexCell] = mutableList[indexNull]
                    .also { mutableList[indexNull] = mutableList[indexCell] }
                mutableList
            } else {
                println("не корректное число !")
                list
            }
        }

    }

    fun myPrint(list: List<Int?>) {
        for ((i, v) in list.withIndex()) {
            if (i % DIM == 0) repeat(2) { println() }
            print(String.format("%-5s", (v ?: "")))
        }
    }
}

