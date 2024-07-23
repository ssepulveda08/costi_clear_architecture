package com.ssepulveda.presentation_report.di

import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import com.ssepulveda.costi.domain.useCase.reports.GetReportMonthsUseCase
import com.ssepulveda.costi.domain.useCase.UseCase
import com.ssepulveda.costi.domain.useCase.reports.GetReportMonthDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ReportModule {


    @Provides
    fun provideGetReportMonthsUseCase(
        configuration: UseCase.Configuration,
        localReportForMonthRepository: LocalReportForMonthRepository,
    ): GetReportMonthsUseCase = GetReportMonthsUseCase(
        configuration,
        localReportForMonthRepository,
    )

    @Provides
    fun provideGetReportMonthDetailUseCase(
        configuration: UseCase.Configuration,
        localReportForMonthRepository: LocalReportForMonthRepository,
    ): GetReportMonthDetailUseCase = GetReportMonthDetailUseCase(
        configuration,
        localReportForMonthRepository,
    )

}