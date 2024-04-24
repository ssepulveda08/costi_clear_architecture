package com.ssepulveda.costi.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.ssepulveda.costi.data.repository.bills.LocalBillRepositoryImpl
import com.ssepulveda.costi.data.repository.costType.LocalCostTypeRepositoryImpl
import com.ssepulveda.costi.data.repository.dataStore.LocalConfigurationRepositoryImpl
import com.ssepulveda.costi.data.repository.report.LocalReportForMonthRepositoryImpl
import com.ssepulveda.costi.data.repository.subType.LocalSubTypeRepositoryImpl
import com.ssepulveda.costi.data.source.local.AppDatabase
import com.ssepulveda.costi.data.source.local.dao.BillEntityDao
import com.ssepulveda.costi.data.source.local.dao.ReportForMonthDao
import com.ssepulveda.costi.data.source.local.dao.SubTypeDao
import com.ssepulveda.costi.data.source.local.dao.TypeOfExpenseDao
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCES_NAME)

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "costs-database"
        ).build()
    }


    @Provides
    fun provideTypeOfExpenseDao(database: AppDatabase): TypeOfExpenseDao = database.typeOfExpenseDao()

    @Provides
    fun provideSubTypeDao(database: AppDatabase): SubTypeDao = database.subTypeDao()

    @Provides
    fun provideBillEntityDao(database: AppDatabase): BillEntityDao = database.billEntityDao()

    @Provides
    fun provideReportForMonthDao(database: AppDatabase): ReportForMonthDao = database.reportForMonthDao()

    @Provides
    fun provideLocalCostTypeRepository(
        typeOfExpenseDao: TypeOfExpenseDao,
    ): LocalCostTypeRepository = LocalCostTypeRepositoryImpl(
        typeOfExpenseDao,
    )

    @Provides
    fun provideLocalSubTypeRepository(
        subTypeDao: SubTypeDao,
    ): LocalSubTypeRepository = LocalSubTypeRepositoryImpl(
        subTypeDao,
    )

    @Provides
    fun provideLocalConfigurationRepositoryImpl(
        @ApplicationContext context: Context
    ): LocalConfigurationRepository = LocalConfigurationRepositoryImpl(
        context.dataStore,
    )

    @Provides
    fun provideLocalBillRepositoryImpl(
        billEntityDao: BillEntityDao
    ): LocalBillRepository = LocalBillRepositoryImpl(
        billEntityDao
    )

    @Provides
    fun provideLocalReportForMonthRepositoryImpl(
        reportForMonthDao: ReportForMonthDao,
    ): LocalReportForMonthRepository = LocalReportForMonthRepositoryImpl(
        reportForMonthDao
    )
}
