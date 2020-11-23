package com.example.exampleapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exampleapp.feature.dataset.DatasetViewModel
import com.example.exampleapp.feature.dataset.repository.DatasetRepository

class ViewModelFactory(
    private val datasetRepository: DatasetRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DatasetViewModel::class.java)) {
            return DatasetViewModel(datasetRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}