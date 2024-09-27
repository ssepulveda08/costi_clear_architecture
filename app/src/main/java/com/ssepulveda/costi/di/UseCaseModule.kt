package com.ssepulveda.costi.di

import com.ssepulveda.costi.domain.repository.AccountRepository
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import com.ssepulveda.costi.domain.useCase.bill.DeleteBillUseCase
import com.ssepulveda.costi.domain.useCase.reports.GetAllBillsByMonthUseCase
import com.ssepulveda.costi.domain.useCase.types.GetCostTypeUseCase
import com.ssepulveda.costi.domain.useCase.types.GetCostTypeWithSubTypeUseCase
import com.ssepulveda.costi.domain.useCase.config.GetCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.reports.GetHomeInformationUseCase
import com.ssepulveda.costi.domain.useCase.config.GetInitialConfigurationUseCase
import com.ssepulveda.costi.domain.useCase.types.GetTypesAndSubTypesUseCase
import com.ssepulveda.costi.domain.useCase.bill.SaveBillUseCase
import com.ssepulveda.costi.domain.useCase.config.SaveCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.config.SaveInitialConfigurationByDefaultUseCase
import com.ssepulveda.costi.domain.useCase.config.UpdateInitialConfigurationUseCase
import com.ssepulveda.costi.domain.useCase.UseCase
import com.ssepulveda.costi.domain.useCase.account.AddAccountUseCase
import com.ssepulveda.costi.domain.useCase.account.GetAccountsByMonthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUseCaseConfiguration(): UseCase.Configuration = UseCase.Configuration(Dispatchers.IO)

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
        accountRepository: AccountRepository,
        localConfigurationRepository: LocalConfigurationRepository,
    ): SaveInitialConfigurationByDefaultUseCase = SaveInitialConfigurationByDefaultUseCase(
        configuration,
        localCostTypeRepository,
        localSubTypeRepository,
        accountRepository,
        localConfigurationRepository,
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

    @Provides
    fun provideDeleteBillUseCase(
        configuration: UseCase.Configuration,
        localBillRepository: LocalBillRepository,
    ): DeleteBillUseCase = DeleteBillUseCase(
        configuration,
        localBillRepository,
    )

    @Provides
    fun provideGetAccountsByMonthUseCase(
        configuration: UseCase.Configuration,
        localConfigurationRepository: LocalConfigurationRepository,
        accountRepository: AccountRepository,
    ): GetAccountsByMonthUseCase = GetAccountsByMonthUseCase(
        configuration,
        localConfigurationRepository,
        accountRepository,
    )

    @Provides
    fun provideAddAccountUseCase(
        configuration: UseCase.Configuration,
        localConfigurationRepository: LocalConfigurationRepository,
        accountRepository: AccountRepository,
    ): AddAccountUseCase = AddAccountUseCase(
        configuration,
        localConfigurationRepository,
        accountRepository,
    )

}