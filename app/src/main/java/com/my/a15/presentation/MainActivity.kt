package com.my.a15.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.my.a15.R
import com.my.a15.presentation.ui.Screens.Game.GameScreen
import com.my.a15.presentation.ui.theme._15Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameViewModel: GameViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            _15Theme {
                val shackState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(
                            modifier = Modifier.padding(16.dp),
                            hostState = shackState
                        )
                    },
                    topBar = {
                        MyAppBar(
                            gameViewModel = gameViewModel,
                            snackHost = shackState
                        )
                    }
                ) { paddingValues ->
                    GameScreen(
                        modifier = Modifier.padding(paddingValues),
                        gameViewModel = gameViewModel
                    )
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun MyAppBar(
        modifier: Modifier = Modifier,
        gameViewModel: GameViewModel,
        snackHost: SnackbarHostState,

        ) {
        val scope = rememberCoroutineScope()
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        scope.launch {
                            snackHost.showSnackbar(
                                message = getString(R.string.exit),
                                actionLabel = getString(R.string.ok),
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
                                message = getString(R.string.reset_game),
                                actionLabel = getString(R.string.ok),
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
            title = { Text(stringResource(R.string.app_name)) })
    }
}





