package com.example.exampleapp.feature.dataset.repository

import android.os.Parcelable
import com.example.exampleapp.data.api.models.Dataset
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisplayableDataset(
    val id: Int,
    val name: String,
    val description: String,
    val dataPoints: List<DisplayableDataPoint>
): Parcelable {

    companion object {
        fun createList(dataset: List<Dataset>) = dataset.map {
            DisplayableDataset(
                id = it.id,
                name = it.name,
                description = it.description,
                dataPoints = DisplayableDataPoint.createList(it.dataPoints)
            )
        }
    }

    @Parcelize
    data class DisplayableDataPoint(
        val id: Int,
        val name: String,
        val someIntProperty: Int,
        val someFloatProperty: Float,
        val someBoolProperty: Boolean
    ): Parcelable {
        companion object {
            fun createList(dataPoints: List<Dataset.DataPoint>) = dataPoints.map {
                DisplayableDataPoint(
                    id = it.id,
                    name = it.name,
                    someIntProperty = it.someIntProperty,
                    someFloatProperty = it.someFloatProperty,
                    someBoolProperty = it.someBoolProperty
                )
            }
        }
    }
}