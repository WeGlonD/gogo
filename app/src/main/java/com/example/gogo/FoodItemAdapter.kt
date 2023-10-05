package com.example.gogo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gogo.FoodItem
import com.example.gogo.R

class FoodItemAdapter(private val foodItemList: ArrayList<FoodItem>) : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewCalories: TextView = itemView.findViewById(R.id.textViewCalories)
        val textViewCarboHydrate: TextView = itemView.findViewById(R.id.textViewCarboHydrate)
        val textViewProtein: TextView = itemView.findViewById(R.id.textViewProtein)
        val textViewFat: TextView = itemView.findViewById(R.id.textViewFat)
        val textViewNatrium: TextView = itemView.findViewById(R.id.textViewNatrium)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodItem = foodItemList[position]
        holder.textViewName.text = "Name: ${foodItem.name}"
        holder.textViewCalories.text = "Calories: ${foodItem.calories}"
        holder.textViewCarboHydrate.text = "CarboHydrate: ${foodItem.carbohydrates}"
        holder.textViewProtein.text = "Protein: ${foodItem.protein}"
        holder.textViewFat.text = "Fat: ${foodItem.fat}"
        holder.textViewNatrium.text = "Natrium: ${foodItem.natrium}"
    }

    override fun getItemCount(): Int {
        return foodItemList.size
    }
}
