package com.example.exampleapp.feature.dataset.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exampleapp.databinding.ItemDatasetBinding
import com.example.exampleapp.feature.base.BaseAdapter
import com.jakewharton.rxbinding3.view.clicks
import java.util.concurrent.TimeUnit

class DatasetItemAdapter: BaseAdapter<DisplayableDataset, RecyclerView.ViewHolder>() {
    class ItemViewHolder(var binding: ItemDatasetBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(ItemDatasetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DisplayableDataset) {
        when(holder) {
            is ItemViewHolder -> {
                holder.binding.apply {
                    textViewTitle.text = item.name
                    textViewDescription.text = item.description
                    // forward click to click subject of adapter (see BaseAdapter)
                    viewClickArea.clicks()
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .map { item }
                        .subscribe(clickSubject)
                }
            }
        }
    }


}