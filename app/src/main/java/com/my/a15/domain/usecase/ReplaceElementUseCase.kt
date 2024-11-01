package com.my.a15.domain.usecase

import com.my.a15.data.RepositoryGameImpl
import com.my.a15.domain.MyModelNum

class ReplaceElementUseCase {
    // todo need inject
    val repositoryGameImpl = RepositoryGameImpl()
    operator fun invoke(myModelNum: MyModelNum, indexItem: Int, indexNull: Int): MyModelNum {
        return repositoryGameImpl.replaceElement(
            myModelNum = myModelNum,
            indexItem = indexItem,
            indexNull = indexNull
        )
    }
}