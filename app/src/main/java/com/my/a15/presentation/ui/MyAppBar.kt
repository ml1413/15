package com.my.a15.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.my.a15.R
import com.my.a15.presentation.GameViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyAppBar(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
    isShowAlertExit: MutableState<Boolean>,
    isShowAlertReset: MutableState<Boolean>,
) {
    val gameViewModelState =
        gameViewModel.gameState.observeAsState(GameViewModel.GameState.Initial)
    val title = remember { mutableStateOf("") }
    when (val state = gameViewModelState.value) {
        GameViewModel.GameState.Initial -> {}
        is GameViewModel.GameState.ResumeGame -> {
            title.value =
                stringResource(if (state.myModelNum.isVictory) R.string.victory else R.string.the_game_is_on)
        }

        GameViewModel.GameState.Victory -> {}
    }

    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { isShowAlertExit.value = isShowAlertExit.value.not() },
                content = {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null
                    )
                })
        },
        actions = {
            IconButton(
                onClick = { isShowAlertReset.value = isShowAlertReset.value.not() },
                content = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null
                    )
                })
        },
        title = {
            Text(title.value)
        })
}


