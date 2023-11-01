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

class HouseActivity: AppCompatActivity() {
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
            val intent = Intent(this@HouseActivity, cartActivity::class.java)
            startActivity(intent)
        }

        val restaurant = intent.getStringExtra("restaurant")

        setActivityInfo(restaurant!!)
    }

    fun setActivityInfo(restaurant: String) {
        binding.textView.text = restaurant
        when (restaurant) {
            "채소" ->{
                binding.imageView.setImageResource(R.drawable.vege)
            }
            "과일" ->{
                binding.imageView.setImageResource(R.drawable.fruit)
            }
            "곡물" ->{
                binding.imageView.setImageResource(R.drawable.grain)
            }
            "버섯" ->{
                binding.imageView.setImageResource(R.drawable.mushroom)
            }
            "육류" ->{
                binding.imageView.setImageResource(R.drawable.meat)
            }
            "생선" ->{
                binding.imageView.setImageResource(R.drawable.fish)
            }
            "해조류" ->{
                binding.imageView.setImageResource(R.drawable.seaweed)
            }
            "해산물" ->{
                binding.imageView.setImageResource(R.drawable.seafood)
            }
            "견과류" ->{
                binding.imageView.setImageResource(R.drawable.nuts)
            }
            "유제품" ->{
                binding.imageView.setImageResource(R.drawable.lactose)
            }
            "조미료" ->{
                binding.imageView.setImageResource(R.drawable.condi)
            }
            "양념" ->{
                binding.imageView.setImageResource(R.drawable.sauce)
            }
            "가공식품" ->{
                binding.imageView.setImageResource(R.drawable.processed)
            }
            "음료" ->{
                binding.imageView.setImageResource(R.drawable.drink)
            }
            "라면" ->{
                binding.imageView.setImageResource(R.drawable.ramyun)
            }
        }
        databaseReference = FirebaseDatabase.getInstance().reference

        val houseAdapter = HouseAdapter(foodItemList,this, databaseReference)
        binding.foodRecyclerView.adapter = houseAdapter

        databaseReference.child(restaurant).addValueEventListener(object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val foodItem = dataSnapshot.getValue(FoodItem::class.java)
                    foodItemList.add(foodItem!!)
                }
                houseAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HouseActivity,
                    "Firebase Restaurant Menu Get Failed: " + restaurant, Toast.LENGTH_LONG).show()
            }
        })
    }
}