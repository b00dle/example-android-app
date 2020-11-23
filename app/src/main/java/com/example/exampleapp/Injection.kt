package com.example.exampleapp

import androidx.lifecycle.ViewModelProvider
import com.example.exampleapp.data.api.ExampleApi
import com.example.exampleapp.feature.dataset.repository.DatasetRepository

object Injection {
    fun provideExampleApi(): ExampleApi {
        return ExampleApi.getInstance()
    }

    fun provideDatasetRepository(): DatasetRepository {
        return DatasetRepository(provideExampleApi())
    }

    fun provideViewModelFactory(): ViewModelProvider.NewInstanceFactory {
        return ViewModelFactory(provideDatasetRepository())
    }
}