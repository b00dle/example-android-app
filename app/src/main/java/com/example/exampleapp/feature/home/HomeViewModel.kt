package com.example.exampleapp.feature.home

import androidx.lifecycle.MutableLiveData
import com.example.exampleapp.feature.base.BaseViewModel

class HomeViewModel: BaseViewModel() {
    val leaveToDataset = MutableLiveData<Boolean>()
    val leaveToWebvis = MutableLiveData<Boolean>()

    fun onGetDataClicked() {
        leaveToDataset.postValue(true)
    }

    fun onShowGraphClicked() {
        leaveToWebvis.postValue(true)
    }
}