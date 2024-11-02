package com.my.a15.presentation.di

import com.my.a15.data.FifteenGame
import com.my.a15.data.FifteenGameImpl
import com.my.a15.data.RepositoryGameImpl
import com.my.a15.domain.RepositoryGame
import com.my.a15.domain.usecase.GetStartedModelUseCase
import com.my.a15.domain.usecase.ReplaceElementUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideRepositoryGameImpl(fifteenGame: FifteenGame): RepositoryGame {
        return RepositoryGameImpl(fifteenGame = fifteenGame)
    }

    @Provides
    fun provideGetStartedModelUseCase(repositoryGameImpl: RepositoryGame): GetStartedModelUseCase {
        return GetStartedModelUseCase(repositoryGameImpl = repositoryGameImpl)
    }

    @Provides
    fun provideReplaceElementUseCase(repositoryGameImpl: RepositoryGame): ReplaceElementUseCase {
        return ReplaceElementUseCase(repositoryGameImpl = repositoryGameImpl)
    }
}