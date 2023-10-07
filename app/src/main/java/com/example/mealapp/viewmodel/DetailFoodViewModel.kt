package com.example.mealapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealapp.R
import com.example.mealapp.data.Foods
import com.example.mealapp.network.ApiConfig
import com.example.mealapp.resource.Resources
import kotlinx.coroutines.launch

class DetailFoodViewModel : ViewModel() {

    private val _foodDetail = MutableLiveData<Resources<List<Foods.Meal?>?>>()
    val foodDetail: LiveData<Resources<List<Foods.Meal?>?>>
        get() = _foodDetail

    fun getFoodDetail(context: Context, id: String) {
        _foodDetail.value = Resources.LOADING(true)
        viewModelScope.launch {
            try {
                val data = ApiConfig(context).apiEndpoint.getDetailFoodByIdAsync(id)
                if (data.isSuccessful) {
                    _foodDetail.value = Resources.LOADING(false)

                    val meals = data.body()?.meals
                    if (meals != null)
                        _foodDetail.value = Resources.SUCCESS(meals)
                    else
                        _foodDetail.value = Resources.EMPTY(true)

                } else {
                    _foodDetail.value = Resources.LOADING(false)
                    _foodDetail.value = Resources.FAILURE(context.getString(R.string.error_failed_to_load_data))
                }
            } catch (e: Exception) {
                _foodDetail.value = Resources.LOADING(false)
                _foodDetail.value = Resources.FAILURE(e.message.toString())
            }
        }
    }
}