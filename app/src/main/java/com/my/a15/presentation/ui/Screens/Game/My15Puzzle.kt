package com.my.a15.presentation.ui.Screens.Game

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.my.a15.domain.ColorCell
import com.my.a15.presentation.GameViewModel
import com.my.a15.presentation.ui.theme.colorCorrectPosition


@Composable
fun My15Puzzle(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
) {


    val indexNull = remember { mutableStateOf(-1) }
    val indexItem = remember { mutableStateOf(-1) }


    val swapElement: () -> Unit = {
        gameViewModel.replaceElement(indexItem = indexItem.value, indexNull = indexNull.value)
    }

    var width = 0//width item -how much to move , will be installed on touch
    val localDensity = LocalDensity.current
    val animationDuration = 50
    val targetValuePairXY = remember { mutableStateOf(Pair(0.dp, 0.dp)) }
    //animationX
    val animatedOffsetX = animateDpAsState(
        targetValue = targetValuePairXY.value.first,
        animationSpec = tween(durationMillis = animationDuration),
        finishedListener = { dp ->
            targetValuePairXY.value = Pair(0.dp, 0.dp)
            if (dp.value != 0f) swapElement()
        }
    )
    //animationY
    val animatedOffsetY = animateDpAsState(
        targetValue = targetValuePairXY.value.second,
        animationSpec = tween(durationMillis = animationDuration),
        finishedListener = { dp ->
            targetValuePairXY.value = Pair(0.dp, 0.dp)
            if (dp.value != 0f) swapElement()
        }
    )
    val gameViewModelState =
        gameViewModel.gameState.observeAsState(GameViewModel.GameState.Initial)
    when (val state = gameViewModelState.value) {
        GameViewModel.GameState.Initial -> {}
        GameViewModel.GameState.Victory -> {}
        is GameViewModel.GameState.ResumeGame -> {
            val myModelNum = state.myModelNum
            val listCells = myModelNum.listCells
            val grid = myModelNum.sqrt
            LazyVerticalGrid(
                modifier = modifier
                    .background(
                        MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.small
                    )
                    .pointerInput(grid) {
                        var left = 0f
                        var right = 0f
                        var up = 0f
                        var down = 0f
                        // input swipe
                        detectDragGestures(
                            onDrag = { _, dragAmount ->
                                if (left < -dragAmount.x) left =
                                    -dragAmount.x// the largest number -x
                                if (right < dragAmount.x) right =
                                    dragAmount.x// the largest number x
                                if (up < -dragAmount.y) up = -dragAmount.y// the largest number -y
                                if (down < dragAmount.y) down = dragAmount.y// the largest number y
                            },
                            onDragEnd = {

                                when {
                                    right > left && right > down && right > up && (indexNull.value % grid) != 0 -> {
                                        //swap right
                                        targetValuePairXY.value =
                                            targetValuePairXY.value
                                                .copy(first = with(localDensity) { width.toDp() })
                                        indexItem.value = indexNull.value - 1
                                    }

                                    left > right && left > down && left > -up && ((indexNull.value + 1) % grid) != 0 -> {
                                        //swap left
                                        targetValuePairXY.value =
                                            targetValuePairXY.value
                                                .copy(first = with(localDensity) { -width.toDp() })
                                        indexItem.value = indexNull.value + 1
                                    }

                                    down > left && down > right && down > up && indexNull.value - grid >= 0 -> {
                                        //swap down
                                        targetValuePairXY.value =
                                            targetValuePairXY.value
                                                .copy(second = with(localDensity) { width.toDp() })
                                        indexItem.value = indexNull.value - grid
                                    }

                                    up > down && up > right && up > left && indexNull.value + grid <= listCells.lastIndex -> {
                                        //swap up
                                        targetValuePairXY.value =
                                            targetValuePairXY.value
                                                .copy(second = with(localDensity) { -width.toDp() })
                                        indexItem.value = indexNull.value + grid
                                    }
                                }
                                // reset value
                                left = 0f
                                right = 0f
                                up = 0f
                                down = 0f

                            }
                        )
                    }
                    .aspectRatio(1f),
                columns = GridCells.Fixed(grid),
                contentPadding = PaddingValues(2.dp),
                userScrollEnabled = false
            ) {
                items(listCells.size) { itemIndex ->
                    val item = listCells[itemIndex]
                    if (item == null) indexNull.value = itemIndex
                    item?.let { myCell ->
                        Card(
                            modifier = Modifier
                                .onGloballyPositioned { layoutCoordinates ->
                                    width = layoutCoordinates.size.width
                                }
                                .padding(2.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                    onClick = {}
                                )
                                .offset
                                    (
                                    x = if (indexItem.value == itemIndex) animatedOffsetX.value else 0.dp,
                                    y = if (indexItem.value == itemIndex) animatedOffsetY.value else 0.dp,
                                ),
                            elevation = CardDefaults.cardElevation(8.dp),
                            border = BorderStroke(
                                0.1.dp,
                                color = MaterialTheme.colorScheme.background.copy(alpha = 0.2f)
                            ),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
                            shape = MaterialTheme.shapes.small
                        ) {

                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f),
                                contentAlignment = Alignment.Center
                            )
                            {

                                Text(
                                    text = myCell.num.toString(),
                                    color = when (myCell.colorCell) {
                                        ColorCell.DEFAULT -> MaterialTheme.colorScheme.background
                                        ColorCell.CORRECT_POSITION -> colorCorrectPosition
                                    },
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
}