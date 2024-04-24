package com.ssepulveda.costi.di

import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import com.ssepulveda.costi.domain.useCase.GetAllBillsByMonthUseCase
import com.ssepulveda.costi.domain.useCase.GetCostTypeUseCase
import com.ssepulveda.costi.domain.useCase.GetCostTypeWithSubTypeUseCase
import com.ssepulveda.costi.domain.useCase.GetCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.GetHomeInformationUseCase
import com.ssepulveda.costi.domain.useCase.GetInitialConfigurationUseCase
import com.ssepulveda.costi.domain.useCase.GetTypesAndSubTypesUseCase
import com.ssepulveda.costi.domain.useCase.SaveBillUseCase
import com.ssepulveda.costi.domain.useCase.SaveCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.SaveInitialConfigurationByDefaultUseCase
import com.ssepulveda.costi.domain.useCase.UpdateInitialConfigurationUseCase
import com.ssepulveda.costi.domain.useCase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUseCaseConfiguration(): UseCase.Configuration = UseCase.Configuration(Dispatchers.Unconfined)

    @Provides
    fun provideGetCostTypeUseCase(
        configuration: UseCase.Configuration,
        localCostTypeRepository: LocalCostTypeRepository
    ): GetCostTypeUseCase = GetCostTypeUseCase(
        configuration,
        localCostTypeRepository
    )

    @Provides
    fun provideGetCostTypeWithSubTypeUseCase(
        configuration: UseCase.Configuration,
        localCostTypeRepository: LocalCostTypeRepository,
        localSubTypeRepository: LocalSubTypeRepository,
    ): GetCostTypeWithSubTypeUseCase = GetCostTypeWithSubTypeUseCase(
        configuration,
        localCostTypeRepository,
        localSubTypeRepository
    )

    @Provides
    fun provideGetInitialConfigurationUseCase(
        configuration: UseCase.Configuration,
        localConfigurationRepository: LocalConfigurationRepository
    ): GetInitialConfigurationUseCase = GetInitialConfigurationUseCase(
        configuration, localConfigurationRepository
    )

    @Provides
    fun provideUpdateInitialConfigurationUseCase(
        configuration: UseCase.Configuration,
        localConfigurationRepository: LocalConfigurationRepository
    ): UpdateInitialConfigurationUseCase = UpdateInitialConfigurationUseCase(
        configuration, localConfigurationRepository
    )

    @Provides
    fun provideSaveInitialConfigurationByDefaultUseCase(
        configuration: UseCase.Configuration,
        localCostTypeRepository: LocalCostTypeRepository,
        localSubTypeRepository: LocalSubTypeRepository,
    ): SaveInitialConfigurationByDefaultUseCase = SaveInitialConfigurationByDefaultUseCase(
        configuration,
        localCostTypeRepository,
        localSubTypeRepository
    )

    @Provides
    fun provideSaveCurrentMonthUseCase(
        configuration: UseCase.Configuration,
        localConfigurationRepository: LocalConfigurationRepository
    ): SaveCurrentMonthUseCase = SaveCurrentMonthUseCase(
        configuration, localConfigurationRepository
    )

    @Provides
    fun provideGetCurrentMonthUseCase(
        configuration: UseCase.Configuration,
        localConfigurationRepository: LocalConfigurationRepository
    ): GetCurrentMonthUseCase = GetCurrentMonthUseCase(
        configuration, localConfigurationRepository
    )

    @Provides
    fun provideGetAllBillsByMonthUseCase(
        configuration: UseCase.Configuration,
        localBillRepository: LocalBillRepository
    ): GetAllBillsByMonthUseCase = GetAllBillsByMonthUseCase(
        configuration, localBillRepository
    )

    @Provides
    fun provideGetHomeInformationUseCase(
        configuration: UseCase.Configuration,
        localConfigurationRepository: LocalConfigurationRepository,
        localBillRepository: LocalBillRepository,
        localReportForMonthRepository: LocalReportForMonthRepository,
    ): GetHomeInformationUseCase = GetHomeInformationUseCase(
        configuration,
        localConfigurationRepository,
        localBillRepository,
        localReportForMonthRepository
    )

    @Provides
    fun provideGetTypesAndSubTypesUseCase(
        configuration: UseCase.Configuration,
        localSubTypeRepository: LocalSubTypeRepository,
        localCostTypeRepository: LocalCostTypeRepository
    ): GetTypesAndSubTypesUseCase = GetTypesAndSubTypesUseCase(
        configuration,
        localSubTypeRepository,
        localCostTypeRepository
    )

    @Provides
    fun provideSaveBillUseCase(
        configuration: UseCase.Configuration,
        localBillRepository: LocalBillRepository,
        localConfigurationRepository: LocalConfigurationRepository,
    ): SaveBillUseCase = SaveBillUseCase(
        configuration,
        localBillRepository,
        localConfigurationRepository
    )

}