package com.my.a15.domain.usecase

import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.model.VariantGrid
import javax.inject.Inject

class GetRestartStartedModelUseCase @Inject constructor(private val repositoryGameImpl: RepositoryGame) {
    operator fun invoke(variantGrid: VariantGrid): MyModelNum {
        return repositoryGameImpl.getRestartStartGameModel(variantGrid = variantGrid)
    }
}