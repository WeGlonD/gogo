package com.example.gogo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gogo.databinding.ActivityRestaurantBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RestaurantActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var databaseReference: DatabaseReference
    private var foodItemList = ArrayList<FoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gotofirst.setOnClickListener {
            finish()
        }

        binding.floatingbutton.setOnClickListener {
            val intent = Intent(this@RestaurantActivity, cartActivity::class.java)
            startActivity(intent)
        }

        val restaurant = intent.getStringExtra("restaurant")

        setActivityInfo(restaurant!!)
    }

    fun setActivityInfo(restaurant: String) {
        binding.textView.text = restaurant
        when (restaurant) {
            "Mcdonald's" -> {
                binding.imageView.setImageResource(R.drawable.mcdonalds)
            }
            "LOTTERIA" -> {
                binding.imageView.setImageResource(R.drawable.lotteria)
            }
        }
        databaseReference = FirebaseDatabase.getInstance().reference

        val mcAdapter = McAdapter(foodItemList,this, databaseReference)
        binding.foodRecyclerView.adapter = mcAdapter

        databaseReference.child(restaurant).addValueEventListener(object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val foodItem = dataSnapshot.getValue(FoodItem::class.java)
                    foodItemList.add(foodItem!!)
                }

                mcAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RestaurantActivity,
                    "Firebase Restaurant Menu Get Failed: " + restaurant, Toast.LENGTH_LONG).show()
            }
        })
    }
}