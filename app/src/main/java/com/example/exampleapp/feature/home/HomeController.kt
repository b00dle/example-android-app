package com.example.exampleapp.feature.home

import com.example.exampleapp.R
import com.example.exampleapp.databinding.ControllerHomeBinding
import com.example.exampleapp.feature.base.BaseController

class HomeController: BaseController<ControllerHomeBinding>(R.layout.controller_home) {
    private val viewModel by lazy {
        HomeViewModel()
    }

    override fun onSetupView() {
        binding.lifecycleOwner = this
    }

    override fun onSetupViewBinding() {

    }

    override fun onSetupViewModelBinding() {

    }
}