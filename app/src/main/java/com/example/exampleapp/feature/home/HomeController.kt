package com.example.exampleapp.feature.home

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import com.example.exampleapp.BuildConfig
import com.example.exampleapp.R
import com.example.exampleapp.databinding.ControllerHomeBinding
import com.example.exampleapp.feature.base.BaseController
import com.example.exampleapp.feature.dataset.DatasetController
import com.example.exampleapp.utils.observe
import com.example.exampleapp.utils.pushAndFade
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.TimeUnit

class HomeController: BaseController<ControllerHomeBinding>(R.layout.controller_home) {
    companion object {
        private val LINKED_API_BASE = BuildConfig.API_BASE_URL.let {
            "<a href=\"$it\">$it</a>"
        }
    }

    private val homeText by lazy {
        ctx.getString(R.string.home_text, LINKED_API_BASE).let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(it)
            }
        }
    }

    private val viewModel by lazy {
        HomeViewModel()
    }

    override fun onSetupView() {
        binding.lifecycleOwner = this

        initializeUi()
    }

    private fun initializeUi() {
        binding.textViewDescription.text = homeText
        binding.textViewDescription.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onSetupViewBinding() {
        disposables += binding.buttonContinue.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                viewModel.onContinueClicked()
            }
    }

    override fun onSetupViewModelBinding() {
        viewModel.leaveScreen.observe(this) {
            viewModel.leaveScreen.postValue(null)
            router.pushAndFade(DatasetController())
        }
    }
}