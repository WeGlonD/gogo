package com.example.gogo

import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HouseAdapter (private val foodItemList: ArrayList<FoodItem>, private val context: Context, private val databaseReference: DatabaseReference) : RecyclerView.Adapter<HouseAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val NameText: TextView = itemView.findViewById(R.id.titletextview)
        val CaloriesText: TextView = itemView.findViewById(R.id.text_cal)
        val CarboHydrateText: TextView = itemView.findViewById(R.id.text_ch)
        val ProteinText: TextView = itemView.findViewById(R.id.text_pr)
        val FatText: TextView = itemView.findViewById(R.id.text_ft)
        val NatriumText: TextView = itemView.findViewById(R.id.text_nt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test2, parent, false)
        return HouseAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseAdapter.ViewHolder, position: Int) {
        val foodItem = foodItemList[position]
        holder.NameText.text = foodItem.name
        holder.CaloriesText.text = "${foodItem.calories}"
        holder.CarboHydrateText.text = "${foodItem.carbohydrates}"
        holder.ProteinText.text = "${foodItem.protein}"
        holder.FatText.text = "${foodItem.fat}"
        holder.NatriumText.text = "${foodItem.natrium}"

        holder.itemView.setOnClickListener {
            showCustomDialog(foodItem)
        }
    }

    override fun getItemCount(): Int {
        return foodItemList.size
    }

    private fun showCustomDialog(foodItem: FoodItem) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.popup_house, null)

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER
        }
        dialog.window?.attributes = layoutParams

        val bigMacNameTextView = dialogView.findViewById<TextView>(R.id.house_name)
        val bigMacCalTextView = dialogView.findViewById<TextView>(R.id.house_cal)
        val bigMacChTextView = dialogView.findViewById<TextView>(R.id.house_ch)
        val bigMacPrTextView = dialogView.findViewById<TextView>(R.id.house_pr)
        val bigMacFtTextView = dialogView.findViewById<TextView>(R.id.house_ft)
        val bigMacNtTextView = dialogView.findViewById<TextView>(R.id.house_nt)

        bigMacNameTextView.text = "${foodItem.name} 1(g)"
        bigMacCalTextView.text = foodItem.calories.toString()
        bigMacChTextView.text = foodItem.carbohydrates.toString()
        bigMacPrTextView.text = foodItem.protein.toString()
        bigMacFtTextView.text = foodItem.fat.toString()
        bigMacNtTextView.text = foodItem.natrium.toString()

        val closeButton = dialogView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        val addButton = dialogView.findViewById<Button>(R.id.addbutton)
        addButton.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid

            val edtext = dialogView.findViewById<EditText>(R.id.howmuch)
            val howmuch = edtext.text.toString().toIntOrNull()
            if (howmuch != null){
                val mediaPlayer = MediaPlayer.create(context, R.raw.click)
                mediaPlayer.start()

                foodItem.protein *= howmuch
                foodItem.natrium *= howmuch
                foodItem.carbohydrates *= howmuch
                foodItem.fat *= howmuch
                foodItem.calories *= howmuch

                val databasePath = "cart/$uid"
                val foodItemRef = databaseReference.child(databasePath).push()
                foodItemRef.setValue(foodItem).addOnSuccessListener {
                    foodItem.protein /= howmuch
                    foodItem.natrium /= howmuch
                    foodItem.carbohydrates /= howmuch
                    foodItem.fat /= howmuch
                    foodItem.calories /= howmuch
                    dialog.dismiss()
                }
            }else{
                Toast.makeText(dialogView.context, "숫자를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}