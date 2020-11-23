package com.example.exampleapp.feature.home

import androidx.lifecycle.MutableLiveData
import com.example.exampleapp.feature.base.BaseViewModel

class HomeViewModel: BaseViewModel() {
    val leaveScreen = MutableLiveData<Boolean>()

    fun onContinueClicked() {
        leaveScreen.postValue(true)
    }
}