package com.example.mealapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mealapp.Helper.toast
import com.example.mealapp.R
import com.example.mealapp.databinding.ActivityDetailFoodBinding
import com.example.mealapp.resource.Resources
import com.example.mealapp.viewmodel.DetailFoodViewModel

private lateinit var binding: ActivityDetailFoodBinding
private lateinit var viewModel: DetailFoodViewModel

private var id: String = ""

class DetailFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailFoodViewModel::class.java]

        initIntent()
        initObserver()
        setupView()
    }

    private fun setupView() {
        binding.btnBack.setOnClickListener { onBackPressed() }
        requestFoodData(id)
    }

    private fun requestFoodData(id: String) {
        viewModel.getFoodDetail(this, id)
    }

    private fun initObserver() {
        onObserveFood()
    }

    private fun onObserveFood() {
        viewModel.foodDetail.observe(this) { response ->
            when (response) {
                is Resources.LOADING -> binding.progressBar.isVisible = response.state
                is Resources.SUCCESS -> {
                    binding.tvInstruction.isVisible = true
                    binding.tvIngredients.isVisible = true

                    val meal = response.data?.get(0)
                    with(binding) {
                        meal?.let {
                            imgFood.load(meal.strMealThumb)
                            tvFoodTitle.text = meal.strMeal
                            tvFoodInstruction.text = meal.strInstructions
                            tvFoodIngredients.text = resources.getString(
                                R.string.label_food_ingredients,
                                "${meal.strIngredient1} (${meal.strMeasure1})\n" +
                                        "${meal.strIngredient2} (${meal.strMeasure2})\n" +
                                        "${meal.strIngredient3} (${meal.strMeasure3})\n" +
                                        "${meal.strIngredient4} (${meal.strMeasure4})\n" +
                                        "${meal.strIngredient5} (${meal.strMeasure5})\n" +
                                        "${meal.strIngredient6} (${meal.strMeasure6})\n" +
                                        "${meal.strIngredient7} (${meal.strMeasure7})\n" +
                                        "${meal.strIngredient8} (${meal.strMeasure8})\n" +
                                        "${meal.strIngredient9} (${meal.strMeasure9})\n" +
                                        "${meal.strIngredient10} (${meal.strMeasure10})\n" +
                                        "${meal.strIngredient11} (${meal.strMeasure11})\n" +
                                        "${meal.strIngredient12} (${meal.strMeasure12})\n"
                            )
                        }
                    }
                }

                is Resources.FAILURE -> toast(response.e)
                is Resources.EMPTY -> binding.tvEmptyData.isVisible = response.isEmpty
            }
        }
    }

    private fun initIntent() {
        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("id").toString()
        }
    }
}