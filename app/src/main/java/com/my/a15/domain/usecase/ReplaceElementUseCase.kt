package com.my.a15.domain.usecase

import com.my.a15.domain.model.MyModelNum
import com.my.a15.domain.RepositoryGame
import javax.inject.Inject

class ReplaceElementUseCase @Inject constructor(private val repositoryGameImpl: RepositoryGame) {

    operator fun invoke(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum {
        return repositoryGameImpl.replaceElement(
            myModelNum = myModelNum,
            indexItem = indexItem,
            indexNull = indexNull
        )
    }
}