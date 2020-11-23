package com.example.exampleapp.feature.dataset

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exampleapp.Injection
import com.example.exampleapp.R
import com.example.exampleapp.databinding.ControllerDatasetBinding
import com.example.exampleapp.feature.base.BaseController
import com.example.exampleapp.feature.dataset.repository.DatasetItemAdapter
import com.example.exampleapp.utils.observe
import io.reactivex.rxkotlin.plusAssign

class DatasetController: BaseController<ControllerDatasetBinding>(R.layout.controller_dataset) {
    private lateinit var datasetAdapter: DatasetItemAdapter

    private val viewModel by lazy {
        viewModelProvider(Injection.provideViewModelFactory())
            .get(DatasetViewModel::class.java)
    }

    override fun onSetupView() {
        binding.lifecycleOwner = this

        datasetAdapter = DatasetItemAdapter()

        initializeUi()
    }

    override fun onSetupViewBinding() {
        disposables += datasetAdapter.clickSubject.subscribe {
            viewModel.onDatesetClicked(it)
        }
    }

    override fun onSetupViewModelBinding() {
        viewModel.datasets.observe(this) {
            if(it.isNotEmpty())
                datasetAdapter.setItems(ArrayList(it))
            else
                datasetAdapter.clearItems()
        }

        viewModel.updateDatasets()
    }

    private fun initializeUi() {
        binding.recyclerView.layoutManager = LinearLayoutManager(ctx)
        binding.recyclerView.adapter = datasetAdapter
    }
}