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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.my.a15.ui.theme._15Theme
import com.my.a15.ui.theme.colorCorrectPosition
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val fifteenEngine = FifteenEngine
        setContent {
            _15Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    val list = remember { mutableStateOf(fifteenEngine.getInitialState()) }
                    val stepCount = remember { mutableStateOf(0) }
                    val isVictory =
                        remember { derivedStateOf { fifteenEngine.isWin(state = list.value) } }
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Victory(isVictory = isVictory)
                        CountStep(
                            modifier = Modifier.weight(0.1f),
                            tempCount = stepCount
                        )
                        My15Puzzle(
                            modifier = Modifier.weight(1f),
                            list = list,
                            onStepListener = { stepCount.value++ },
                            onSwapListener = { indexSwipeItem, indexEmptyItem ->
                                list.value = fifteenEngine.transitionState(
                                    oldState = list.value,
                                    index1 = indexSwipeItem,
                                    index2 = indexEmptyItem
                                )
                            },
                            onCheckPosition = { item, itemIndex ->
                                return@My15Puzzle fifteenEngine.checkPosition(
                                    cell = item,
                                    index = itemIndex
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun Victory(
        modifier: Modifier = Modifier,
        isVictory: State<Boolean>
    ) {
        val text =
            stringResource(if (isVictory.value) R.string.victory else R.string.the_game_is_on)
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
        )
    }

    @Composable
    private fun CountStep(
        modifier: Modifier = Modifier,
        tempCount: MutableState<Int>
    ) {
        val countStep = remember { mutableStateOf(0) }
        LaunchedEffect(key1 = tempCount.value) {
            delay(600)
            repeat(tempCount.value - countStep.value) {
                delay(100)
                countStep.value++
            }
        }
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(countStep.value.toString(), style = MaterialTheme.typography.displaySmall)
        }
    }
}

@Composable
fun My15Puzzle(
    modifier: Modifier = Modifier,
    list: MutableState<List<Int?>>,
    onStepListener: () -> Unit,
    onSwapListener: (indexSwipeItem: Int, indexEmptyItem: Int) -> Unit,
    onCheckPosition: (item: Int, indexItem: Int) -> Boolean
) {

    var indexNull: Int? = null
    var width = 0
    val animationDuration = 100

    val indexSwipeItem = remember { mutableStateOf(0) }
    val indexEmptyItem = remember { mutableStateOf(0) }

    val localDensity = LocalDensity.current

    val targetValuePairXY = remember { mutableStateOf(Pair(0.dp, 0.dp)) }
    val swapElement: () -> Unit =
        {
            onStepListener()
            onSwapListener(indexSwipeItem.value, indexEmptyItem.value)
        }
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
                    modifier = Modifier
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
                        modifier = Modifier
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    )
                    {

                        Text(
                            text = it.toString(),
                            color =
                            when (onCheckPosition(item, itemIndex)) {
                                true -> colorCorrectPosition
                                false -> MaterialTheme.colorScheme.background
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



