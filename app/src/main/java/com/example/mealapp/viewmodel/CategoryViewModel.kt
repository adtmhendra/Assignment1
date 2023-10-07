package com.example.mealapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealapp.R
import com.example.mealapp.data.Areas
import com.example.mealapp.data.Categories
import com.example.mealapp.data.Foods
import com.example.mealapp.network.ApiConfig
import com.example.mealapp.resource.Resources
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _categoryResponse = MutableLiveData<Resources<List<Categories.Category?>?>>()
    val categoryResponse: LiveData<Resources<List<Categories.Category?>?>>
        get() = _categoryResponse

    fun getCategories(context: Context) {
        _categoryResponse.value = Resources.LOADING(true)
        viewModelScope.launch {
            try {
                val data = ApiConfig(context).apiEndpoint.getCategoriesAsync()
                if (data.isSuccessful) {
                    _categoryResponse.value = Resources.LOADING(false)

                    val meals = data.body()?.meals
                    if (meals != null)
                        _categoryResponse.value = Resources.SUCCESS(meals)
                    else
                        _categoryResponse.value = Resources.EMPTY(true)

                } else {
                    _categoryResponse.value = Resources.LOADING(false)
                    _categoryResponse.value =
                        Resources.FAILURE(context.getString(R.string.error_failed_to_load_data))
                }
            } catch (e: Exception) {
                _categoryResponse.value = Resources.LOADING(false)
                _categoryResponse.value = Resources.FAILURE(e.message.toString())
            }
        }
    }

    private val _areaResponse = MutableLiveData<Resources<List<Areas.Area?>?>>()
    val areaResponse: LiveData<Resources<List<Areas.Area?>?>>
        get() = _areaResponse

    fun getAreas(context: Context) {
        _areaResponse.value = Resources.LOADING(true)
        viewModelScope.launch {
            try {
                val data = ApiConfig(context).apiEndpoint.getAreasAsync()
                if (data.isSuccessful) {
                    _areaResponse.value = Resources.LOADING(false)

                    val meals = data.body()?.meals
                    if (meals != null)
                        _areaResponse.value = Resources.SUCCESS(meals)
                    else
                        _areaResponse.value = Resources.EMPTY(true)

                } else {
                    _areaResponse.value = Resources.LOADING(false)
                    _areaResponse.value =
                        Resources.FAILURE(context.getString(R.string.error_failed_to_load_data))
                }
            } catch (e: Exception) {
                _areaResponse.value = Resources.LOADING(false)
                _areaResponse.value = Resources.FAILURE(e.message.toString())
            }
        }
    }

    private val _foodsByCategory = MutableLiveData<Resources<List<Foods.Meal?>?>>()
    val foodsByCategory: LiveData<Resources<List<Foods.Meal?>?>>
        get() = _foodsByCategory

    fun getFoodsByCategory(context: Context, food: String) {
        _foodsByCategory.value = Resources.LOADING(true)
        viewModelScope.launch {
            try {
                val data = ApiConfig(context).apiEndpoint.getFoodsByCategoryAsync(food)
                if (data.isSuccessful) {
                    _foodsByCategory.value = Resources.LOADING(false)

                    val meals = data.body()?.meals
                    if (meals != null)
                        _foodsByCategory.value = Resources.SUCCESS(meals)
                    else
                        _foodsByCategory.value = Resources.EMPTY(true)

                } else {
                    _foodsByCategory.value = Resources.LOADING(false)
                    _foodsByCategory.value =
                        Resources.FAILURE(context.getString(R.string.error_failed_to_load_data))
                }
            } catch (e: Exception) {
                _foodsByCategory.value = Resources.LOADING(false)
                _foodsByCategory.value = Resources.FAILURE(e.message.toString())
            }
        }
    }

    private val _foodsByArea = MutableLiveData<Resources<List<Foods.Meal?>?>>()
    val foodsByArea: LiveData<Resources<List<Foods.Meal?>?>>
        get() = _foodsByArea

    fun getFoodsByArea(context: Context, food: String) {
        _foodsByArea.value = Resources.LOADING(true)
        viewModelScope.launch {
            try {
                val data = ApiConfig(context).apiEndpoint.getFoodsByAreaAsync(food)
                if (data.isSuccessful) {
                    _foodsByArea.value = Resources.LOADING(false)

                    val meals = data.body()?.meals
                    if (meals != null)
                        _foodsByArea.value = Resources.SUCCESS(meals)
                    else
                        _foodsByArea.value = Resources.EMPTY(true)

                } else {
                    _foodsByArea.value = Resources.LOADING(false)
                    _foodsByArea.value =
                        Resources.FAILURE(context.getString(R.string.error_failed_to_load_data))
                }
            } catch (e: Exception) {
                _foodsByArea.value = Resources.LOADING(false)
                _foodsByArea.value = Resources.FAILURE(e.message.toString())
            }
        }
    }

    private val _foodsBySearch = MutableLiveData<Resources<List<Foods.Meal?>?>>()
    val foodsBySearch: LiveData<Resources<List<Foods.Meal?>?>>
        get() = _foodsBySearch

    fun getFoodsBySearch(context: Context, food: String) {
        _foodsBySearch.value = Resources.LOADING(true)
        viewModelScope.launch {
            try {
                val data = ApiConfig(context).apiEndpoint.getFoodsBySearchAsync(food)
                if (data.isSuccessful) {
                    _foodsBySearch.value = Resources.LOADING(false)

                    val meals = data.body()?.meals
                    if (meals != null)
                        _foodsBySearch.value = Resources.SUCCESS(meals)
                    else
                        _foodsBySearch.value = Resources.EMPTY(true)

                } else {
                    _foodsBySearch.value = Resources.LOADING(false)
                    _foodsBySearch.value =
                        Resources.FAILURE(context.getString(R.string.error_failed_to_load_data))
                }
            } catch (e: Exception) {
                _foodsBySearch.value = Resources.LOADING(false)
                _foodsBySearch.value = Resources.FAILURE(e.message.toString())
            }
        }
    }

    init {
        _categoryResponse.value = Resources.EMPTY(true)
        _areaResponse.value = Resources.EMPTY(true)
    }
}