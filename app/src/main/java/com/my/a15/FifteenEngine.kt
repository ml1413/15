package com.my.a15

import android.util.Log

interface FifteenEngine {
    fun transitionState(oldState: List<Int?>, index1: Int, index2: Int): List<Int?>
    fun isWin(state: List<Int?>): Boolean
    fun getInitialState(): List<Int?>
    fun checkPosition(cell: Int, index: Int): Boolean

    companion object : FifteenEngine {
        const val EMPTY = 16
        const val DIM = 4
        val FINAL_STATE = (1..15) + null
        
        override fun transitionState(oldState: List<Int?>, index1: Int, index2: Int): List<Int?> {
            return replace2(oldState, index1, index2)
        }

        override fun isWin(state: List<Int?>)= state == FINAL_STATE

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

        override fun checkPosition(cell: Int, index: Int): Boolean =
            cell == FINAL_STATE[index]


        private fun replace2(oldState: List<Int?>, index1: Int, index2: Int): List<Int?> {
            val newList = oldState.toMutableList()
            newList[index1] = newList[index2].also { newList[index2] = newList[index1] }
            return newList.toList()
        }

    }

}

