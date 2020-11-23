package com.example.exampleapp.feature.dataset

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.exampleapp.feature.base.BaseViewModel
import com.example.exampleapp.feature.dataset.repository.DatasetRepository
import com.example.exampleapp.feature.dataset.repository.DisplayableDataset
import io.reactivex.rxkotlin.addTo

class DatasetViewModel(private val datasetRepository: DatasetRepository): BaseViewModel() {

    val datasets = MutableLiveData<List<DisplayableDataset>>()

    fun updateDatasets() {
        datasetRepository.getDatasets()
            .map { DisplayableDataset.createList(it) }
            .subscribe({
                datasets.postValue(it)
            },{
                throw it
            }).addTo(disposables)
    }

    fun onDatesetClicked(dataset: DisplayableDataset) {
        Log.d("Example", "clicked dataset with name '" + dataset.name + "'")
    }

}