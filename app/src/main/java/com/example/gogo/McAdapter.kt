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
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import java.net.URLEncoder
import java.net.URLDecoder
class McAdapter (private val foodItemList: ArrayList<FoodItem>, private val context: Context, private val databaseReference: DatabaseReference, val restaurant: String) : RecyclerView.Adapter<McAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val NameText: TextView = itemView.findViewById(R.id.titletextview)
        val CaloriesText: TextView = itemView.findViewById(R.id.text_cal)
        val CarboHydrateText: TextView = itemView.findViewById(R.id.text_ch)
        val ProteinText: TextView = itemView.findViewById(R.id.text_pr)
        val FatText: TextView = itemView.findViewById(R.id.text_ft)
        val NatriumText: TextView = itemView.findViewById(R.id.text_nt)
        val imageView: ImageView = itemView.findViewById(R.id.titleimageview)

        fun loadImage(restaurant: String, name: String) {
            val storageReference = FirebaseStorage.getInstance().getReference("$restaurant/$name.png")
            GlideApp.with(itemView.context).load(storageReference).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): McAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test2, parent, false)
        return McAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: McAdapter.ViewHolder, position: Int) {
        val foodItem = foodItemList[position]
        holder.NameText.text = foodItem.name
        holder.CaloriesText.text = "${foodItem.calories}"
        holder.CarboHydrateText.text = "${foodItem.carbohydrates}"
        holder.ProteinText.text = "${foodItem.protein}"
        holder.FatText.text = "${foodItem.fat}"
        holder.NatriumText.text = "${foodItem.natrium}"
        holder.loadImage(restaurant, foodItem.name)
        holder.itemView.setOnClickListener {
            showCustomDialog(foodItem)
        }
    }

    override fun getItemCount(): Int {
        return foodItemList.size
    }

    private fun showCustomDialog(foodItem: FoodItem) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.popup_mc_a, null)

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER
        }
        dialog.window?.attributes = layoutParams

        val bigMacNameTextView = dialogView.findViewById<TextView>(R.id.bigmac_name)
        val bigMacCalTextView = dialogView.findViewById<TextView>(R.id.bigmac_cal)
        val bigMacChTextView = dialogView.findViewById<TextView>(R.id.bigmac_ch)
        val bigMacPrTextView = dialogView.findViewById<TextView>(R.id.bigmac_pr)
        val bigMacFtTextView = dialogView.findViewById<TextView>(R.id.bigmac_ft)
        val bigMacNtTextView = dialogView.findViewById<TextView>(R.id.bigmac_nt)
        val bigMacImageView = dialogView.findViewById<ImageView>(R.id.imageView)

        fun loadImage(restaurant: String, name: String) {
            val storageReference = FirebaseStorage.getInstance().getReference("$restaurant/$name.png")
            GlideApp.with(dialogView.context).load(storageReference).into(bigMacImageView)
        }

        bigMacNameTextView.text = foodItem.name
        bigMacCalTextView.text = foodItem.calories.toString()
        bigMacChTextView.text = foodItem.carbohydrates.toString()
        bigMacPrTextView.text = foodItem.protein.toString()
        bigMacFtTextView.text = foodItem.fat.toString()
        bigMacNtTextView.text = foodItem.natrium.toString()
        loadImage(restaurant, foodItem.name)
        val closeButton = dialogView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        val addButton = dialogView.findViewById<Button>(R.id.addbutton)
        addButton.setOnClickListener {

            val mediaPlayer = MediaPlayer.create(context, R.raw.click)
            mediaPlayer.start()

            val currentUser = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid

            if (uid != null) {
                val databasePath = "cart/$uid"
                val foodItemRef = databaseReference.child(databasePath).push()
                foodItemRef.setValue(foodItem).addOnSuccessListener {
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }
}