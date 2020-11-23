package com.example.exampleapp.feature.dataset.repository

import com.example.exampleapp.BuildConfig
import com.example.exampleapp.data.api.ExampleApi

class DatasetRepository(private val api: ExampleApi) {
    fun getDatasets() = api.getDatasets(accessToken = BuildConfig.API_ACCESS_TOKEN)
}