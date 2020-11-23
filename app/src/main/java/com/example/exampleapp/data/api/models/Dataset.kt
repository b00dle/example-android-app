package com.example.exampleapp.data.api.models

import com.squareup.moshi.Json

data class Dataset(val id: Int, val name: String, val description: String, @Json(name = "data_points") val dataPoints: List<DataPoint>) {
    data class DataPoint(
        val id: Int,
        val name: String,
        @Json(name = "some_int_property") val someIntProperty: Int,
        @Json(name = "some_float_property") val someFloatProperty: Float,
        @Json(name = "some_bool_property") val someBoolProperty: Boolean
    )
}