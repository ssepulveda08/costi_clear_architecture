package com.ssepulveda.costi.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

   /* @Binds
    abstract fun bindLocalCostTypeRepository(localCostTypeRepository: LocalCostTypeRepositoryImpl): LocalCostTypeRepository

    @Binds
    abstract fun bindLocalSubTypeRepositoryImpl(localCostTypeRepository: LocalSubTypeRepositoryImpl): LocalSubTypeRepository*/
}