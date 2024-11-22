package com.my.a15.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid
import com.my.a15.domain.usecase.GetRestartStartedModelUseCase
import com.my.a15.domain.usecase.GetStartedUseCase
import com.my.a15.domain.usecase.ReplaceElementUseCase
import com.my.a15.domain.usecase.SaveInStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "GameViewModel"

@HiltViewModel
class GameViewModel @Inject constructor(
    private val replaceElementUseCase: ReplaceElementUseCase,
    private val saveInStorageUseCase: SaveInStorageUseCase,
    private val getStartedUseCase: GetStartedUseCase,
    private val getRestartStartedModelUseCase: GetRestartStartedModelUseCase
) : ViewModel() {

    private val _gameState = MutableLiveData<GameState>(GameState.Initial)
    val gameState: LiveData<GameState> = _gameState

    init {
        viewModelScope.launch {
            val myModelNum = getStartedUseCase()
            _gameState.value = GameState.ResumeGame(myModelNum = myModelNum)
        }
    }

    fun restartGame(variantGrid: VariantGrid) {
        val restartModel = getRestartStartedModelUseCase(variantGrid = variantGrid)
        _gameState.value = GameState.ResumeGame(myModelNum = restartModel)
    }

    fun replaceElement(indexItem: Int, indexNull: Int) {
        _gameState.value?.state(
            resumeGame = { myModelNum ->
                val newModel =
                    replaceElementUseCase(
                        myModelNum = myModelNum,
                        indexItem = indexItem,
                        indexNull = indexNull
                    )
                _gameState.value = GameState.ResumeGame(myModelNum = newModel)
                Log.i(TAG, "replaceElement: newModel $newModel")
            }
        )

    }

    fun saveToStorage() {
        _gameState.value?.state(
            resumeGame = { myModelNum ->
                viewModelScope.launch {
                    saveInStorageUseCase(myModelNum = myModelNum)
                    Log.i(TAG, "saveToStorage: myModelNum $myModelNum")
                }
            }
        )
    }

    sealed class GameState() {
        object Initial : GameState()
        class ResumeGame(val myModelNum: MyModelNum) : GameState()
        object Victory : GameState()
    }

    /** other method _____________________________________________________________________________*/
    private fun GameState.state(
        initial: () -> Unit = {},
        resumeGame: (MyModelNum) -> Unit = {},
        victory: () -> Unit = {}
    ) {
        when (this) {
            is GameState.ResumeGame -> {
                Log.i(TAG, "state: myModelNum $myModelNum")
                resumeGame(myModelNum)
            }
            GameState.Initial -> initial()
            GameState.Victory -> victory()
        }
    }
}