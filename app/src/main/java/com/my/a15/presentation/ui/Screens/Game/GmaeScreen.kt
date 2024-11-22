package com.my.a15.presentation.ui.Screens.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.a15.presentation.GameViewModel
import com.my.a15.presentation.ui.MyLifeCycle

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        CountStep(
            modifier = Modifier,
            gameViewModel = gameViewModel,
        )
        My15Puzzle(
            modifier = Modifier,
            gameViewModel = gameViewModel,
        )
    }
}

