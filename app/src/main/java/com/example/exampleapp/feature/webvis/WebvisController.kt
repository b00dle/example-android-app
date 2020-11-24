package com.example.exampleapp.feature.webvis

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.exampleapp.BuildConfig
import com.example.exampleapp.R
import com.example.exampleapp.databinding.ControllerWebvisBinding
import com.example.exampleapp.feature.base.BaseController

class WebvisController: BaseController<ControllerWebvisBinding>(R.layout.controller_webvis) {

    private val visualizationUrl by lazy {
        BuildConfig.API_BASE_URL + "para-cords"
    }


    override fun onSetupView() {
        binding.lifecycleOwner = this

        initializeUi()
    }

    override fun onSetupViewBinding() {

    }

    override fun onSetupViewModelBinding() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initializeUi() {
        // this enables trusting our own server, otherwise cors will prevent loading our web app
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if(request?.url?.toString()?.startsWith(BuildConfig.API_BASE_URL) == true)
                    return true
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        // this enables proper evaluation of javascript elements returned by the server
        binding.webView.settings.apply {
            domStorageEnabled = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
            defaultTextEncodingName = "utf-8"
        }

        binding.webView.loadUrl(visualizationUrl)
    }
}