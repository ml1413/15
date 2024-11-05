package com.my.a15.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.a15.R
import com.my.a15.presentation.ui.MyAppBar
import com.my.a15.presentation.ui.Screens.Game.GameScreen
import com.my.a15.presentation.ui.theme._15Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameViewModel: GameViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            _15Theme {
                val snackState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(
                            modifier = Modifier.padding(16.dp),
                            hostState = snackState
                        )
                    },
                    topBar = {
                        MyAppBar(
                            gameViewModel = gameViewModel,
                            snackHost = snackState
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


}




