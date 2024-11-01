package com.my.a15.presentation.ui.Screens.Game

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.my.a15.R
import com.my.a15.presentation.GameViewModel

@Composable
fun VictoryComponent(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
) {
    val gameViewModelState =
        gameViewModel.gameState.observeAsState(GameViewModel.GameState.Initial)
    when (val state = gameViewModelState.value) {
        is GameViewModel.GameState.ResumeGame -> {
            val text =
                stringResource(if (state.myModelNum.isVictory) R.string.victory else R.string.the_game_is_on)
            Text(
                text = text,
                style = MaterialTheme.typography.displayMedium,
            )
        }

        else -> {}
    }

}