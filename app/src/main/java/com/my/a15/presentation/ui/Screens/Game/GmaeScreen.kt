package com.my.a15.presentation.ui.Screens.Game

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.a15.presentation.GameViewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VictoryComponent(gameViewModel = gameViewModel)
        CountStep(
            modifier = Modifier.weight(0.1f),
            gameViewModel = gameViewModel,
        )
        My15Puzzle(
            modifier = Modifier.weight(1f),
            gameViewModel = gameViewModel,
        )
    }
}