package com.example.mealapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealapp.Helper.createChip
import com.example.mealapp.Helper.startActivity
import com.example.mealapp.Helper.toast
import com.example.mealapp.data.Foods
import com.example.mealapp.databinding.ActivityMainBinding
import com.example.mealapp.resource.Resources
import com.example.mealapp.view.DetailFoodActivity
import com.example.mealapp.viewmodel.CategoryViewModel
import com.google.android.material.chip.Chip

private lateinit var binding: ActivityMainBinding
private lateinit var viewModel: CategoryViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        setupView()
        initObserver()
    }

    private fun setupView() {
        binding.chipGroupCategory.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let {
                if (it.text == resources.getString(R.string.label_food_type)) {
                    binding.chipGroupFilter.removeAllViews()
                    requestDataCategory()
                } else {
                    binding.chipGroupFilter.removeAllViews()
                    requestDataArea()
                }
            }
        }

        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.edtSearch.clearFocus()
                requestDataFoodsBySearch(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun requestDataCategory() {
        viewModel.getCategories(this)
    }

    private fun requestDataArea() {
        viewModel.getAreas(this)
    }

    private fun requestDataFoodsByCategory(food: String) {
        viewModel.getFoodsByCategory(this, food)
    }

    private fun requestDataFoodsByArea(food: String) {
        viewModel.getFoodsByArea(this, food)
    }

    private fun requestDataFoodsBySearch(food: String) {
        viewModel.getFoodsBySearch(this@MainActivity, food)
    }

    private fun initObserver() {
        onObserveCategory()
        onObserveArea()
        onObserveFoodsByCategory()
        onObserveFoodsByArea()
        onObserveFoodsBySearch()
    }

    private fun onObserveCategory() {
        viewModel.categoryResponse.observe(this) { response ->
            when (response) {
                is Resources.LOADING -> {
                    with(binding) {
                        tvEmptyData.isVisible = false
                        progressBar.isVisible = response.state
                    }
                }

                is Resources.SUCCESS -> {
                    binding.tvFilter.isVisible = true

                    repeat(response.data?.indices!!.count()) {
                        val categoryName = response.data[it]?.strCategory
                        categoryName?.let { name ->
                            val chip = createChip(layoutInflater, name)
                            binding.chipGroupFilter.addView(chip)
                        }
                    }

                    binding.chipGroupFilter.setOnCheckedChangeListener { group, checkedId ->
                        val chips: Chip? = group.findViewById(checkedId)
                        chips?.let { chip ->
                            requestDataFoodsByCategory(chip.text.toString())
                        }
                    }
                }

                is Resources.FAILURE -> toast(response.e)
                is Resources.EMPTY -> binding.tvEmptyData.isVisible = response.isEmpty
            }
        }
    }

    private fun onObserveArea() {
        viewModel.areaResponse.observe(this) { response ->
            when (response) {
                is Resources.LOADING -> {
                    with(binding) {
                        tvEmptyData.isVisible = false
                        progressBar.isVisible = response.state
                    }
                }

                is Resources.SUCCESS -> {
                    binding.tvFilter.isVisible = true

                    repeat(response.data?.indices!!.count()) {
                        val areaName = response.data[it]?.strArea
                        areaName?.let { area ->
                            val chip = createChip(layoutInflater, area)
                            binding.chipGroupFilter.addView(chip)
                        }
                    }

                    binding.chipGroupFilter.setOnCheckedChangeListener { group, checkedId ->
                        val chips: Chip? = group.findViewById(checkedId)
                        chips?.let { chip ->
                            requestDataFoodsByArea(chip.text.toString())
                        }
                    }
                }

                is Resources.FAILURE -> toast(response.e)
                is Resources.EMPTY -> binding.tvEmptyData.isVisible = response.isEmpty
            }
        }
    }

    private fun onObserveFoodsBySearch() {
        viewModel.foodsBySearch.observe(this) { response ->
            when (response) {
                is Resources.LOADING -> {
                    with(binding) {
                        tvEmptyData.isVisible = false
                        progressBar.isVisible = response.state
                    }
                }

                is Resources.SUCCESS -> {
                    binding.tvEmptyData.isVisible = false
                    val data = response.data
                    data?.let { setupFoodsRecyclerView(it) }
                }

                is Resources.FAILURE -> toast(response.e)
                is Resources.EMPTY -> binding.tvEmptyData.isVisible = response.isEmpty
            }
        }
    }

    private fun onObserveFoodsByCategory() {
        viewModel.foodsByCategory.observe(this) { response ->
            when (response) {
                is Resources.LOADING -> {
                    with(binding) {
                        tvEmptyData.isVisible = false
                        progressBar.isVisible = response.state
                    }
                }

                is Resources.SUCCESS -> {
                    val data = response.data
                    data?.let { setupFoodsRecyclerView(it) }
                }

                is Resources.FAILURE -> toast(response.e)
                is Resources.EMPTY -> binding.tvEmptyData.isVisible = response.isEmpty
            }
        }
    }

    private fun onObserveFoodsByArea() {
        viewModel.foodsByArea.observe(this) { response ->
            when (response) {
                is Resources.LOADING -> {
                    with(binding) {
                        tvEmptyData.isVisible = false
                        progressBar.isVisible = response.state
                    }
                }

                is Resources.SUCCESS -> {
                    val data = response.data
                    data?.let { setupFoodsRecyclerView(it) }
                }

                is Resources.FAILURE -> toast(response.e)
                is Resources.EMPTY -> binding.tvEmptyData.isVisible = response.isEmpty
            }
        }
    }

    private fun setupFoodsRecyclerView(foods: List<Foods.Meal?>) {
        val foodsAdapter = FoodsAdapter { food ->
            startActivity(
                DetailFoodActivity::class.java,
                bundleOf(
                    "id" to food?.idMeal.toString()
                )
            )
        }

        with(binding.rvFoods) {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = foodsAdapter
        }
        foodsAdapter.setData(foods)
        foodsAdapter.notifyDataSetChanged()
    }
}