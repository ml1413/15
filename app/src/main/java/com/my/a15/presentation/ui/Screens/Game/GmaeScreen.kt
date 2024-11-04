package com.my.a15.presentation.ui.Screens.Game

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.my.a15.data.FifteenGameImpl
import com.my.a15.data.RepositoryGameImpl
import com.my.a15.domain.usecase.GetStartedModelUseCase
import com.my.a15.domain.usecase.ReplaceElementUseCase
import com.my.a15.presentation.GameViewModel

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

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun Prew(modifier: Modifier = Modifier) {
    val fifteenGameImpl = FifteenGameImpl()
    val repositoryGameImpl = RepositoryGameImpl(fifteenGameImpl)
    val getStartedModelUseCase = GetStartedModelUseCase(repositoryGameImpl)
    val replaceElementUseCase = ReplaceElementUseCase(repositoryGameImpl)
    val gameViewModel = GameViewModel(getStartedModelUseCase, replaceElementUseCase)
    GameScreen(gameViewModel = gameViewModel)
}