package com.my.a15.presentation.ui.Screens.Game

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
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

    val gameViewModelState =
        gameViewModel.gameState.observeAsState(GameViewModel.GameState.Initial)

    val indexNull = remember { mutableStateOf(-1) }
    val indexItem = remember { mutableStateOf(-1) }


    val swapElement: () -> Unit = {
        gameViewModel.replaceElement(indexItem = indexItem.value, indexNull = indexNull.value)
    }

    var width = 0//width item -how much to move , will be installed on touch
    val localDensity = LocalDensity.current
    val animationDuration = 100
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
    when (val state = gameViewModelState.value) {
        GameViewModel.GameState.Initial -> {}
        GameViewModel.GameState.Victory -> {}
        is GameViewModel.GameState.ResumeGame -> {
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
                val listCells = state.myModelNum.listCells
                items(listCells.size) { itemIndex ->
                    val item = listCells[itemIndex]
                    if (item == null) indexNull.value = itemIndex
                    item?.let { myCell ->
                        Card(
                            modifier = Modifier
                                .onGloballyPositioned { layoutCoordinates ->
                                    width = layoutCoordinates.size.width
                                }
                                .pointerInput(Unit) {
                                    // input swipe
                                    detectDragGestures { _, dragAmount ->
                                        when {
                                            dragAmount.x > 0 && indexNull.value == itemIndex + 1 -> {
                                                //swap right
                                                targetValuePairXY.value =
                                                    targetValuePairXY.value
                                                        .copy(first = with(localDensity) { width.toDp() })
                                                indexItem.value = itemIndex
                                            }

                                            dragAmount.x < 0 && indexNull.value == itemIndex - 1 -> {
                                                //swap left
                                                targetValuePairXY.value =
                                                    targetValuePairXY.value
                                                        .copy(first = with(localDensity) { -width.toDp() })
                                                indexItem.value = itemIndex
                                            }

                                            dragAmount.y > 0 && indexNull.value == itemIndex + 4 -> {
                                                //swap top
                                                targetValuePairXY.value =
                                                    targetValuePairXY.value
                                                        .copy(second = with(localDensity) { width.toDp() })
                                                indexItem.value = itemIndex
                                            }

                                            dragAmount.y < 0 && indexNull.value == itemIndex - 4 -> {
                                                //swap bottom
                                                targetValuePairXY.value =
                                                    targetValuePairXY.value
                                                        .copy(second = with(localDensity) { -width.toDp() })
                                                indexItem.value = itemIndex
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
                                    x = if (indexItem.value == itemIndex) animatedOffsetX.value else 0.dp,
                                    y = if (indexItem.value == itemIndex) animatedOffsetY.value else 0.dp,
                                ),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground)
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