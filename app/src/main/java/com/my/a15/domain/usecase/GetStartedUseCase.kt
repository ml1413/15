package com.my.a15.domain.usecase

import com.my.a15.data.game.VariantGrid
import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.model.MyModelNum
import javax.inject.Inject

private const val TAG = "GetStartedUseCase"

class GetStartedUseCase @Inject constructor(private val repositoryGameImpl: RepositoryGame) {
    suspend operator fun invoke(): MyModelNum {
        val myModelNumFromStorage = repositoryGameImpl.getFromStorage()
        val myModelNumFromGame = repositoryGameImpl.getStartGameModel(VariantGrid.GRID_4X4)
        return myModelNumFromStorage ?: myModelNumFromGame
    }
}