package com.my.a15.domain.usecase

import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid
import javax.inject.Inject

class GetStartedModelUseCase@Inject constructor(private val repositoryGameImpl: RepositoryGame) {
    operator fun invoke(): MyModelNum {
        return repositoryGameImpl.getStartGameModel()
    }
}