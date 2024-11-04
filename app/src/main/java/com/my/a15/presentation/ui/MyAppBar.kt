package com.my.a15.presentation.ui

import android.provider.Settings.Global.getString
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.my.a15.R
import com.my.a15.presentation.GameViewModel
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyAppBar(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel,
    snackHost: SnackbarHostState,

    ) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
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
                onClick = {
                    scope.launch {
                        snackHost.showSnackbar(
                            message =  ContextCompat.getString(context,R.string.exit),
                            actionLabel = ContextCompat.getString(context,R.string.ok),
                            withDismissAction = true
                        ).apply {
                            if (this == SnackbarResult.ActionPerformed) exitProcess(0)
                        }
                    }
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null
                    )
                })
        },
        actions = {
            IconButton(
                onClick = {
                    scope.launch {
                        snackHost.showSnackbar(
                            message =  ContextCompat.getString(context,R.string.reset_game),
                            actionLabel =  ContextCompat.getString(context,R.string.ok),
                            withDismissAction = true
                        ).apply {
                            if (this == SnackbarResult.ActionPerformed) gameViewModel.restartGame()
                        }
                    }
                },
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