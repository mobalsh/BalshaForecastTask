package com.balsha.forecasttask.di

import com.balsha.forecasttask.api.ApiService
import com.balsha.forecasttask.data.repository.MainRepository
import com.balsha.forecasttask.data.repository.MainRepositoryImpl
import com.balsha.forecasttask.domain.usecase.GetForecastUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMainRepository(apiService: ApiService): MainRepository {
        return MainRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetForecastUseCase(mainRepository: MainRepository): GetForecastUseCase {
        return GetForecastUseCase(mainRepository)
    }
}