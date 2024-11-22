package com.my.a15.domain.usecase

import android.util.Log
import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid
import javax.inject.Inject

private const val TAG = "GetStartedUseCase"

class GetStartedUseCase @Inject constructor(private val repositoryGameImpl: RepositoryGame) {
    suspend operator fun invoke(): MyModelNum {
        val myModelNumFromStorage = repositoryGameImpl.getFromStorage()

        Log.i(TAG, "invoke:finalList ${myModelNumFromStorage}")

        val myModelNumFromGame = repositoryGameImpl. getStartGameModel()
        return myModelNumFromStorage ?: myModelNumFromGame
    }
}