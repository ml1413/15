package com.my.a15.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.my.a15.presentation.ui.Screens.Game.GameScreen
import com.my.a15.presentation.ui.theme._15Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameViewModel: GameViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            _15Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    GameScreen(
                        modifier = Modifier.padding(paddingValues),
                        gameViewModel = gameViewModel
                    )
                }
            }
        }
    }
}





