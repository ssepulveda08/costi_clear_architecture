package com.ssepulveda.costi.data.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ssepulveda.costi.data.repository.account.AccountRepositoryImpl
import com.ssepulveda.costi.data.repository.bills.LocalBillRepositoryImpl
import com.ssepulveda.costi.data.repository.costType.LocalCostTypeRepositoryImpl
import com.ssepulveda.costi.data.repository.dataStore.LocalConfigurationRepositoryImpl
import com.ssepulveda.costi.data.repository.report.LocalReportForMonthRepositoryImpl
import com.ssepulveda.costi.data.repository.subType.LocalSubTypeRepositoryImpl
import com.ssepulveda.costi.data.source.local.AppDatabase
import com.ssepulveda.costi.data.source.local.MIGRATION_1_2
import com.ssepulveda.costi.data.source.local.dao.AccountDao
import com.ssepulveda.costi.data.source.local.dao.BillEntityDao
import com.ssepulveda.costi.data.source.local.dao.ReportForMonthDao
import com.ssepulveda.costi.data.source.local.dao.SubTypeDao
import com.ssepulveda.costi.data.source.local.dao.TypeOfExpenseDao
import com.ssepulveda.costi.domain.repository.AccountRepository
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
import java.util.concurrent.Executors
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
        ).addCallback(RoomDatabaseCallback())
            .fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_2)
            .setQueryCallback({ sqlQuery, bindArgs ->
            Log.d("SQL Query:",  "$sqlQuery SQL Args: $bindArgs")
        }, Executors.newSingleThreadExecutor())
            .build()
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
    fun provideAccountDao(database: AppDatabase): AccountDao = database.accountDao()

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
        billEntityDao: BillEntityDao,
    ): LocalReportForMonthRepository = LocalReportForMonthRepositoryImpl(
        reportForMonthDao,
        billEntityDao
    )
    @Provides
    fun provideAccountRepository(
        accountDao: AccountDao,
    ): AccountRepository = AccountRepositoryImpl(
        accountDao
    )
}

class RoomDatabaseCallback : RoomDatabase.Callback() {
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        try {
            // Puedes probar operaciones aquí
        } catch (e: Exception) {
            Log.e("RoomDatabase", "Error durante la migración: ${e.message}")
        }
    }

    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
        super.onDestructiveMigration(db)
        Log.e("RoomDatabase", "Migración destructiva realizada")
    }
}