package com.balsha.forecasttask.di

import android.content.Context
import androidx.room.Room
import com.balsha.forecasttask.api.ApiService
import com.balsha.forecasttask.data.database.ForecastDao
import com.balsha.forecasttask.data.database.ForecastDatabase
import com.balsha.forecasttask.data.repository.MainRepository
import com.balsha.forecasttask.data.repository.MainRepositoryImpl
import com.balsha.forecasttask.domain.usecase.AddForecastToDBUseCase
import com.balsha.forecasttask.domain.usecase.GetForecastFromApiUseCase
import com.balsha.forecasttask.domain.usecase.GetForecastFromDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideForecastDatabase(@ApplicationContext appContext: Context): ForecastDatabase {
        return Room.databaseBuilder(
            appContext,
            ForecastDatabase::class.java,
            "forecast_database"
        ).build()
    }

    @Provides
    fun provideForecastDao(forecastDatabase: ForecastDatabase): ForecastDao {
        return forecastDatabase.forecastDao()
    }

    @Provides
    @Singleton
    fun provideMainRepository(apiService: ApiService, forecastDao: ForecastDao): MainRepository {
        return MainRepositoryImpl(apiService, forecastDao)
    }

    @Provides
    @Singleton
    fun provideGetForecastFromApiUseCase(mainRepository: MainRepository): GetForecastFromApiUseCase {
        return GetForecastFromApiUseCase(mainRepository)
    }

    @Provides
    @Singleton
    fun provideGetForecastFromDBUseCase(mainRepository: MainRepository): GetForecastFromDBUseCase {
        return GetForecastFromDBUseCase(mainRepository)
    }

    @Provides
    @Singleton
    fun provideAddForecastToDBUseCase(mainRepository: MainRepository): AddForecastToDBUseCase {
        return AddForecastToDBUseCase(mainRepository)
    }
}