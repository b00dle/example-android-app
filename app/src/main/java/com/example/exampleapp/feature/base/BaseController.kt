package com.example.exampleapp.feature.base

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.exampleapp.MainActivity
import io.reactivex.disposables.CompositeDisposable
import work.beltran.conductorviewmodel.ViewModelController

abstract class BaseController<T: ViewDataBinding>(@LayoutRes private val layout: Int) : ViewModelController(), LifecycleObserver {

    protected lateinit var binding: T

    protected val disposables = CompositeDisposable()

    val ctx: Context by lazy(LazyThreadSafetyMode.NONE) { applicationContext!! }

    protected val appCompatActivity: AppCompatActivity
        get() = activity as AppCompatActivity

    protected val mainActivity by lazy {
        activity as MainActivity
    }

    protected val sharedPreferences: SharedPreferences by lazy {
        mainActivity.sharedPreferences
    }

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = this

        lifecycle.addObserver(this)

        onCreateMenu()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onLifecycleCreate() {
        onSetupView()
        onSetupViewModelBinding()
        Log.d("b00dle", "onLifecycleCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifecycleStart() {
        Log.d("b00dle", "onLifecycleStart")
        onSetupViewBinding()
        onConfigureMainActivity()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onLifecycleStop() {
        disposables.clear()
        onStorePersistentState()
        Log.d("b00dle", "lifecycle stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleDestroy() {
        onCleanup()
        disposables.clear()
        lifecycle.removeObserver(this)
    }

    // ----------------------------------------------------------------------------

    abstract fun onSetupView()
    abstract fun onSetupViewBinding()
    abstract fun onSetupViewModelBinding()
    open fun onCreateMenu() {}
    protected open fun onStorePersistentState() {}
    protected open fun onRestorePersistentState() {}
    protected open fun onConfigureMainActivity() {
        mainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }
    protected open fun onCleanup() {}

    // ----------------------------------------------------------------------------
}