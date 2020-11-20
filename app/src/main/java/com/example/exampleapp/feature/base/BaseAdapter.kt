package com.example.exampleapp.feature.base

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

abstract class BaseAdapter<T, VH> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PropertySubject<PT>(
        private val onChange: (pt: PT) -> PT,
        var changeRequested: PublishSubject<PT> = PublishSubject.create<PT>(),
        var hasChanged: PublishSubject<PT> = PublishSubject.create<PT>())
    {
        fun change(pt: PT) = hasChanged.onNext(onChange(pt))
    }

    protected val items = ArrayList<T>()

    var clickSubject = PublishSubject.create<T>()

    // ----------------------------------------------------------------------------

    fun clearItems() {
        this.items.clear()

        notifyDataSetChanged()
    }

    fun setItems(items: ArrayList<T>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    fun setItem(item: T, position: Int) {
        items[position] = item
        notifyItemChanged(position)
    }

    fun extendItems(items: ArrayList<T>) {
        val startChange = this.items.lastIndex
        var changeCount = 0
        items.forEach {
            if(!this.items.contains(it)) {
                this.items.add(it)
                changeCount += 1
            }
        }
        if(changeCount > 0)
            notifyItemRangeInserted(startChange, changeCount)
    }

    open fun indexOf(element: T): Int {
        return items.indexOf(element)
    }

    // ----------------------------------------------------------------------------

    final override fun getItemCount() = items.size

    final override fun getItemViewType(position: Int) = getItemViewType(items[position])

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[holder.adapterPosition]

        /*holder.itemView.clicks()
            .throttleFirst(1, TimeUnit.SECONDS)
            .map { item }
            .subscribe(clickSubject)*/

        onBindViewHolder(holder as VH, item)
    }

    // ----------------------------------------------------------------------------

    open fun getItemViewType(item: T) = 0
    open fun onBindViewHolder(holder: VH, item: T) {}
}