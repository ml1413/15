package com.my.a15.presentation.di

import android.content.Context
import androidx.room.Room
import com.my.a15.data.game.FifteenGame
import com.my.a15.data.game.FifteenGameImpl
import com.my.a15.data.repository.RepositoryGameImpl
import com.my.a15.data.storage.Room.FifteenDB
import com.my.a15.data.storage.Room.StorageImpl
import com.my.a15.data.storage.Storage
import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.usecase.GetStartedModelUseCase
import com.my.a15.domain.usecase.GetStartedUseCase
import com.my.a15.domain.usecase.ReplaceElementUseCase
import com.my.a15.domain.usecase.SaveInStorageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideFifteenGameImpl(): FifteenGame {
        return FifteenGameImpl()
    }

    @Singleton
    @Provides
    fun provideRepositoryGameImpl(fifteenGame: FifteenGame, storage: Storage): RepositoryGame {
        return RepositoryGameImpl(fifteenGame = fifteenGame, storage = storage)
    }

    @Provides
    fun provideGetStartedModelUseCase(repositoryGameImpl: RepositoryGame): GetStartedModelUseCase {
        return GetStartedModelUseCase(repositoryGameImpl = repositoryGameImpl)
    }

    @Provides
    fun provideReplaceElementUseCase(repositoryGameImpl: RepositoryGame): ReplaceElementUseCase {
        return ReplaceElementUseCase(repositoryGameImpl = repositoryGameImpl)
    }

    @Provides
    fun provideSaveInStorageUseCase(repositoryGameImpl: RepositoryGame): SaveInStorageUseCase {
        return SaveInStorageUseCase(repositoryGameImpl = repositoryGameImpl)
    }

    @Provides
    fun provideGetStartedUseCase(repositoryGameImpl: RepositoryGame): GetStartedUseCase {
        return GetStartedUseCase(repositoryGameImpl = repositoryGameImpl)
    }

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): FifteenDB {
        return Room.databaseBuilder(
            context = context,
            klass = FifteenDB::class.java,
            name = FifteenDB.NAME_DB
        ).build()
    }

    @Provides
    @Singleton
    fun provideStorage(db: FifteenDB): Storage {
        return StorageImpl(db = db)
    }
}