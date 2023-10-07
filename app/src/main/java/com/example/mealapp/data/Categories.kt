package com.example.mealapp.data


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Categories(
    @Json(name = "meals")
    val meals: List<Category?>?,
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Category(
        @Json(name = "strCategory")
        val strCategory: String,
    )
}

@Keep
@JsonClass(generateAdapter = true)
data class Areas(
    @Json(name = "meals")
    val meals: List<Area>,
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Area(
        @Json(name = "strArea")
        val strArea: String,
    )
}