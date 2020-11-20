package com.example.exampleapp.feature.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val disposables = CompositeDisposable()

    // ----------------------------------------------------------------------------

    override fun onCleared() {
        disposables.clear()
        disposables.dispose()
    }
}