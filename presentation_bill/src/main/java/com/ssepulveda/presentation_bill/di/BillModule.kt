package com.ssepulveda.presentation_bill.di

import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import com.ssepulveda.costi.domain.useCase.bill.GetBillByIdUseCase
import com.ssepulveda.costi.domain.useCase.bill.UpdateBillUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BillModule {

    @Provides
    fun provideGetBillByIdUseCase(
        configuration: UseCase.Configuration,
        localBillRepository: LocalBillRepository,
    ): GetBillByIdUseCase = GetBillByIdUseCase(
        configuration,
        localBillRepository,
    )

    @Provides
    fun provideUpdateBillUseCase(
        configuration: UseCase.Configuration,
        localBillRepository: LocalBillRepository,
    ): UpdateBillUseCase = UpdateBillUseCase(
        configuration,
        localBillRepository,
    )

}