package com.my.a15

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.a15.ui.theme._15Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _15Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Greeting(paddingValues = paddingValues)
                }
            }
        }
    }
}
@Composable
fun Greeting(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    Log.d("TAG1", "Greeting: ")
    val list = remember { mutableStateOf((listOf(null) + (1..15)).shuffled()) }

    val itemWidth = remember { mutableStateOf(0) }
    var width = 0
    val indexSwipeItem = remember { mutableStateOf(0) }
    val indexEmptyItem = remember { mutableStateOf(0) }
    val isHorizontal = remember { mutableStateOf(true) }
    //animation
    val animatedOffset = animateDpAsState(
        targetValue = with(LocalDensity.current) { itemWidth.value.toDp() },
        animationSpec = tween(durationMillis = 100),
        finishedListener = { dp ->
            itemWidth.value = 0
            if (dp.value != 0f) {
                list.value = list.value.swap(indexSwipeItem.value, indexEmptyItem.value)
            }
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
                                var indexNull = -1
                                // input swipe
                                detectDragGestures { _, dragAmount ->
                                    indexNull = if (indexNull < 1) {
                                        list.value.indexOf(null)
                                    } else -1

                                    if (dragAmount.x > 0 && indexNull == itemIndex + 1) {
                                        isHorizontal.value = true
                                        indexSwipeItem.value = itemIndex
                                        indexEmptyItem.value = itemIndex + 1
                                        itemWidth.value = width
                                    } else if (dragAmount.x < 0 && indexNull == itemIndex - 1) {
                                        isHorizontal.value = true
                                        indexSwipeItem.value = itemIndex
                                        indexEmptyItem.value = itemIndex - 1
                                        itemWidth.value = -width

                                    } else
                                        if (dragAmount.y > 0 && indexNull == itemIndex + 4) {
                                            isHorizontal.value = false
                                            indexSwipeItem.value = itemIndex
                                            indexEmptyItem.value = itemIndex + 4
                                            itemWidth.value = width
                                        } else if (dragAmount.y < 0 && indexNull == itemIndex - 4) {
                                            isHorizontal.value = false
                                            indexSwipeItem.value = itemIndex
                                            indexEmptyItem.value = itemIndex - 4
                                            itemWidth.value = -width

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
                                x = if (indexSwipeItem.value == itemIndex && isHorizontal.value) animatedOffset.value else 0.dp,
                                y = if (indexSwipeItem.value == itemIndex && isHorizontal.value.not()) animatedOffset.value else 0.dp,
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
                                color = MaterialTheme.colorScheme.background,
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
        Greeting()
    }
}

fun <T> List<T>.swap(index1: Int, index2: Int): List<T> {
    val newList = this.toMutableList()
    newList[index1] = newList[index2].also { newList[index2] = newList[index1] }
    return newList.toList()
}

private fun checkPeripheryNum(value: Int, list: List<Int?>): Boolean {
    val index = list.indexOf(null)
    val leftNum = if (index % 4 == 0) null else list[index - 1]
    val rightNum = if ((index + 1) % 4 == 0) null else list[index + 1]
    val upNum = if (index < 4) null else list[index - 4]
    val bottomNum = if (index + 4 > list.lastIndex) null else list[index + 4]
    val listNum = listOf(leftNum, rightNum, upNum, bottomNum)
    return listNum.contains(value)
}