package com.tolga.stocks_playground.di

import com.tolga.stocks_playground.data.repository.FinnhubRepositoryImpl
import com.tolga.stocks_playground.domain.repository.FinnhubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFinnhubRepository(
        finnhubRepositoryImpl: FinnhubRepositoryImpl
    ): FinnhubRepository
}