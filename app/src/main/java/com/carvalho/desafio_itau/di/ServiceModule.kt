package com.carvalho.desafio_itau.di

import com.carvalho.desafio_itau.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providerRepository(): Repository{
        return Repository()
    }

}