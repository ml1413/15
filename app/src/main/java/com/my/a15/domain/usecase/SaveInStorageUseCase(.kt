package com.my.a15.domain.usecase

import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.model.MyModelNum
import javax.inject.Inject

class SaveInStorageUseCase @Inject constructor(private val repositoryGameImpl: RepositoryGame) {
    suspend operator fun invoke(myModelNum: MyModelNum) {
        repositoryGameImpl.saveToStorage(myModelNum = myModelNum)
    }
}