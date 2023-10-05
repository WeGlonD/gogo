package com.example.gogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.gogo.databinding.ActivityCartBinding
import com.google.firebase.database.*

class cartActivity : ComponentActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화
        database = FirebaseDatabase.getInstance()

        // Mc 노드에서 데이터 읽어오기
        val mcReference: DatabaseReference = database.getReference("Mc")

        mcReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val foodItem = postSnapshot.getValue(FoodItem::class.java)

                    // 읽어온 데이터를 사용하여 화면에 표시
                    if (foodItem != null) {
                        binding.textViewName.text = "Name: ${foodItem.name}"
                        binding.textViewCalories.text = "Calories: ${foodItem.calories}"
                        binding.textViewCarboHydrate.text = "CarboHydrate: ${foodItem.carbohydrates}"
                        binding.textViewProtein.text = "Protein: ${foodItem.protein}"
                        binding.textViewFat.text = "Fat: ${foodItem.fat}"
                        binding.textViewNatrium.text = "Natrium: ${foodItem.natrium}"
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터를 읽어오지 못했을 때의 처리
            }
        })
    }
}
