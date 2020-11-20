package com.example.exampleapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.exampleapp.databinding.ActivityMainBinding
import com.example.exampleapp.feature.home.HomeController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var router: Router

    private var savedInstanceState: Bundle? = null

    private val preferencesLocation: String by lazy {
        getString(R.string.app_shared_preferences)
    }

    val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(preferencesLocation, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        this.savedInstanceState = savedInstanceState

        initializeRouting()
    }

    private fun initializeRouting() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        router = Conductor.attachRouter(this, binding.changeHandlerFrameLayout, savedInstanceState)
        if(!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(HomeController()))
        }
    }
}