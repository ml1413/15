package com.my.a15.domain.usecase

import android.util.Log
import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.model.MyModelNum
import javax.inject.Inject
private  const val TAG  = "SaveInStorageUseCase"
class SaveInStorageUseCase @Inject constructor(private val repositoryGameImpl: RepositoryGame) {
    suspend operator fun invoke(myModelNum: MyModelNum) {
        Log.i(TAG, "invoke: myModelNum $myModelNum ")
        repositoryGameImpl.saveToStorage(myModelNum = myModelNum)
    }
}