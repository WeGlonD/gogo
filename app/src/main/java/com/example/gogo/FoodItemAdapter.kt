package com.example.gogo

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class FoodItemAdapter(private val foodItemList: ArrayList<FoodItem>, private val keyList:ArrayList<String>, private val dbReference: DatabaseReference) : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewCalories: TextView = itemView.findViewById(R.id.textViewCalories)
        val textViewCarboHydrate: TextView = itemView.findViewById(R.id.textViewCarboHydrate)
        val textViewProtein: TextView = itemView.findViewById(R.id.textViewProtein)
        val textViewFat: TextView = itemView.findViewById(R.id.textViewFat)
        val textViewNatrium: TextView = itemView.findViewById(R.id.textViewNatrium)
        val buttonDelete: Button = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_edit, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodItem = foodItemList[position]
        holder.textViewName.text = foodItem.name
        holder.textViewCalories.text = "${foodItem.calories}"
        holder.textViewCarboHydrate.text = "${foodItem.carbohydrates}"
        holder.textViewProtein.text = "${foodItem.protein}"
        holder.textViewFat.text = "${foodItem.fat}"
        holder.textViewNatrium.text = "${foodItem.natrium}"
        holder.buttonDelete.setOnClickListener {
            val key = keyList[position]
            keyList.clear()
            foodItemList.clear()
            dbReference.child(key).removeValue().addOnSuccessListener {
                Log.d("cartAdapter", ""+position+"번째 지움")
                Log.d("cartAdapter", ""+foodItemList.size)
                notifyItemRemoved(position)
                Log.d("cartAdapter", ""+foodItemList.size)
                notifyItemRangeChanged(position, foodItemList.size)
                Log.d("cartAdapter", ""+foodItemList.size)

                for (k in keyList) {
                    Log.d("cartAdapter", k)
                }
            }.addOnFailureListener {
                Log.d("cartAdapter", "지우기 실패")
            }

        }
    }





    override fun getItemCount(): Int {
        return foodItemList.size
    }
}
