package com.my.a15.domain.usecase

import com.my.a15.data.RepositoryGameImpl
import com.my.a15.domain.MyModelNum

class GetStartedModelUseCase() {
    // todo need inject
    val repositoryGameImpl = RepositoryGameImpl()
    operator fun invoke(): MyModelNum {
        return repositoryGameImpl.getStartGameModel()
    }
}