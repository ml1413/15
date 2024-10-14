package com.my.a15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.a15.ui.theme._15Theme
import com.my.a15.ui.theme.colorCorrectPosition
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _15Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    //todo inject FifteenEngine add fun chect isCorrect position
                    My15Puzzle(paddingValues = paddingValues)
                }
            }
        }
    }

    @Composable
    private fun CountStep() {
        val tempCount = remember { mutableStateOf(0) }
        val countStep = remember { mutableStateOf(0) }
        LaunchedEffect(key1 = tempCount.value) {
            delay(600)
            repeat(tempCount.value - countStep.value) {
                delay(300)
                countStep.value++
            }
        }
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(countStep.value.toString())
        }
    }
}

@Composable
fun My15Puzzle(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    val list = remember { mutableStateOf(generateList()) }
    val listReference = (1..16).toList()

    var indexNull: Int? = null
    var width = 0

    val indexSwipeItem = remember { mutableStateOf(0) }
    val indexEmptyItem = remember { mutableStateOf(0) }

    val localDensity = LocalDensity.current

    val targetValuePairXY = remember { mutableStateOf(Pair(0.dp, 0.dp)) }
    val swapElement: () -> Unit =
        { list.value = list.value.swap(indexSwipeItem.value, indexEmptyItem.value) }
    //animationX
    val animatedOffsetX = animateDpAsState(
        targetValue = targetValuePairXY.value.first,
        animationSpec = tween(durationMillis = 100),
        finishedListener = { dp ->
            targetValuePairXY.value = Pair(0.dp, 0.dp)
            if (dp.value != 0f) swapElement()
        }
    )
    //animationY
    val animatedOffsetY = animateDpAsState(
        targetValue = targetValuePairXY.value.second,
        animationSpec = tween(durationMillis = 100),
        finishedListener = { dp ->
            targetValuePairXY.value = Pair(0.dp, 0.dp)
            if (dp.value != 0f) swapElement()
        }
    )


    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            modifier = modifier
                .aspectRatio(1f)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(list.value.size) { itemIndex ->
                val item = list.value[itemIndex]
                item?.let {
                    Card(
                        modifier = modifier
                            .onGloballyPositioned { layoutCoordinates ->
                                width = layoutCoordinates.size.width
                            }
                            .pointerInput(Unit) {
                                // input swipe
                                detectDragGestures { _, dragAmount ->
                                    indexNull =
                                        if (indexNull == null) list.value.indexOf(null) else null

                                    when {
                                        dragAmount.x > 0 && indexNull == itemIndex + 1 -> {
                                            //swap right
                                            targetValuePairXY.value =
                                                targetValuePairXY.value
                                                    .copy(first = with(localDensity) { width.toDp() })
                                            indexSwipeItem.value = itemIndex
                                            indexEmptyItem.value = itemIndex + 1
                                        }

                                        dragAmount.x < 0 && indexNull == itemIndex - 1 -> {
                                            //swap left
                                            targetValuePairXY.value =
                                                targetValuePairXY.value
                                                    .copy(first = with(localDensity) { -width.toDp() })
                                            indexSwipeItem.value = itemIndex
                                            indexEmptyItem.value = itemIndex - 1
                                        }

                                        dragAmount.y > 0 && indexNull == itemIndex + 4 -> {
                                            //swap top
                                            targetValuePairXY.value =
                                                targetValuePairXY.value
                                                    .copy(second = with(localDensity) { width.toDp() })
                                            indexSwipeItem.value = itemIndex
                                            indexEmptyItem.value = itemIndex + 4
                                        }

                                        dragAmount.y < 0 && indexNull == itemIndex - 4 -> {
                                            //swap bottom
                                            targetValuePairXY.value =
                                                targetValuePairXY.value
                                                    .copy(second = with(localDensity) { -width.toDp() })
                                            indexSwipeItem.value = itemIndex
                                            indexEmptyItem.value = itemIndex - 4
                                        }
                                    }

                                }
                            }
                            .padding(8.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {}
                            )
                            .offset
                                (
                                x = if (indexSwipeItem.value == itemIndex) animatedOffsetX.value else 0.dp,
                                y = if (indexSwipeItem.value == itemIndex) animatedOffsetY.value else 0.dp,
                            ),
                        elevation = CardDefaults.elevatedCardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground)
                    ) {

                        Box(
                            modifier = modifier
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        )
                        {

                            Text(
                                text = it.toString(),
                                color = if (item == listReference[itemIndex])
                                    colorCorrectPosition
                                else
                                    MaterialTheme.colorScheme.background,
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    }
                }

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    _15Theme {
        My15Puzzle()
    }
}

fun <T> List<T>.swap(index1: Int, index2: Int): List<T> {
    val newList = this.toMutableList()
    newList[index1] = newList[index2].also { newList[index2] = newList[index1] }
    return newList.toList()
}




fun generateList(): List<Int?> {
    val l = ((1..15) + null).toMutableList()
    fun isSolvable(tiles: List<Int?>): Boolean {
        val flattened = tiles.filterNotNull() // Убираем null
        val inversions = flattened.indices.sumBy { i ->
            (i + 1 until flattened.size).count { j -> flattened[i] > flattened[j] }
        }

        // Находим позицию пустой клетки (null)
        val gridSize = Math.sqrt(tiles.size.toDouble()).toInt() // Размер сетки (например, 4x4)
        val nullRow = tiles.indexOf(null) / gridSize // Ряд, где находится null

        // Для четного размера поля нужно учитывать как число инверсий, так и позицию пустой клетки
        return if (gridSize % 2 == 1) {
            inversions % 2 == 0 // Если размер сетки нечётный, конфигурация решаема, если инверсий чётное число
        } else {
            (inversions + nullRow) % 2 == 1 // Для чётного размера сетки решаемость зависит от суммы инверсий и позиции null
        }
    }
    do {
        l.shuffle() // Перемешиваем список
    } while (!isSolvable(l)) // Проверяем, решаемая ли конфигурация
    return l
}

