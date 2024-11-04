package com.my.a15.presentation.ui.Screens.Game

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.my.a15.presentation.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun CountStep(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
) {
    val gameViewModelState =
        gameViewModel.gameState.observeAsState(GameViewModel.GameState.Initial)
    when (val state = gameViewModelState.value) {
        is GameViewModel.GameState.ResumeGame -> {
            val modelCountStep = state.myModelNum.countStep
            val countStep = remember { mutableStateOf(modelCountStep) }
            if (countStep.value > modelCountStep) countStep.value = 0
            Log.i("TAG1", "ResumeGame: ")
            LaunchedEffect(key1 = state.myModelNum.countStep) {
                delay(600)
                repeat(state.myModelNum.countStep - countStep.value) {
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

        else -> {}
    }
}