package com.my.a15.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my.a15.domain.MyModelNum
import com.my.a15.domain.usecase.GetStartedModelUseCase
import com.my.a15.domain.usecase.ReplaceElementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getStartedModelUseCase: GetStartedModelUseCase,
    private val replaceElementUseCase: ReplaceElementUseCase
) : ViewModel() {

    private val _gameState = MutableLiveData<GameState>(GameState.Initial)
    val gameState: LiveData<GameState> = _gameState

    init {
        getStartedState()
    }

    private fun getStartedState() {
        _gameState.value = GameState.ResumeGame(myModelNum = getStartedModelUseCase())
    }

    fun restartGame() {
        getStartedState()
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
            is GameState.ResumeGame -> resumeGame(this.myModelNum)
            GameState.Initial -> initial()
            GameState.Victory -> victory()
        }
    }
}