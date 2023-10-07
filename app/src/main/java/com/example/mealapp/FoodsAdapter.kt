package com.example.mealapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mealapp.data.Foods
import com.example.mealapp.databinding.ListFoodBinding
import com.google.android.material.card.MaterialCardView

class FoodsAdapter(private val onClick: (Foods.Meal?) -> Unit) :
    RecyclerView.Adapter<FoodsAdapter.FoodsViewHolder>() {

    private var foods = mutableListOf<Foods.Meal?>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FoodsAdapter.FoodsViewHolder {
        return FoodsViewHolder(ListFoodBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FoodsAdapter.FoodsViewHolder, position: Int) {
        foods.let { it[position] }
        val food = foods[position]
        holder.bind(food)
        holder.card.setOnClickListener { onClick(food) }
    }

    override fun getItemCount() = foods.size

    inner class FoodsViewHolder(private val binding: ListFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Foods.Meal?) {
            with(binding) {
                food?.let {
                    imgFood.load(food.strMealThumb)
                    tvFoodTitle.text = food.strMeal
                }
            }
        }

        val card: MaterialCardView = itemView.findViewById(R.id.card_food)
    }

    fun setData(data: List<Foods.Meal?>) {
        foods.clear()
        foods.addAll(data)
    }
}