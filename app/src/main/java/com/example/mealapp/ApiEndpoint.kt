package com.example.mealapp

import com.example.mealapp.data.Areas
import com.example.mealapp.data.Categories
import com.example.mealapp.data.Foods
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {

    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getCategoriesAsync(): Response<Categories>

    @GET("api/json/v1/1/list.php?a=list")
    suspend fun getAreasAsync(): Response<Areas>

    @GET("api/json/v1/1/filter.php")
    suspend fun getFoodsByCategoryAsync(
        @Query("c") category: String,
    ): Response<Foods>

    @GET("api/json/v1/1/filter.php")
    suspend fun getFoodsByAreaAsync(
        @Query("a") area: String,
    ): Response<Foods>

    @GET("api/json/v1/1/lookup.php")
    suspend fun getDetailFoodByIdAsync(
        @Query("i") id: String,
    ): Response<Foods>

    @GET("api/json/v1/1/search.php?")
    suspend fun getFoodsBySearchAsync(
        @Query("s") food: String,
    ): Response<Foods>
}