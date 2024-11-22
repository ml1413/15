package com.my.a15.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.my.a15.presentation.ui.Alert.MyAlertExit
import com.my.a15.presentation.ui.Alert.MyAlertReset
import com.my.a15.presentation.ui.MyAppBar
import com.my.a15.presentation.ui.MyLifeCycle
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
            MyLifeCycle(onPause = { gameViewModel.saveToStorage() })
            _15Theme {
                val isShowAlertExit = remember { mutableStateOf(false) }
                val isShowAlertReset = remember { mutableStateOf(false) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MyAppBar(
                            gameViewModel = gameViewModel,
                            isShowAlertExit = isShowAlertExit,
                            isShowAlertReset = isShowAlertReset,
                        )
                    }
                ) { paddingValues ->
                    MyAlertReset(
                        modifier = Modifier.padding(paddingValues),
                        gameViewModel = gameViewModel,
                        isShowAlertReset = isShowAlertReset,
                    )
                    MyAlertExit(
                        modifier = Modifier.padding(paddingValues),
                        isShowAlertExit = isShowAlertExit
                    )
                    GameScreen(
                        modifier = Modifier.padding(paddingValues),
                        gameViewModel = gameViewModel
                    )
                }
            }
        }
    }


}




