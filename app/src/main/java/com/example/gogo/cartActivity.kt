package com.example.gogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gogo.databinding.ActivityCartBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class cartActivity : ComponentActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var foodItemList: ArrayList<FoodItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화
        database = FirebaseDatabase.getInstance()

        // Mc 노드에서 데이터 읽어오기
        val mcReference: DatabaseReference = database.getReference("Mc")

        // RecyclerView 초기화
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        foodItemList = ArrayList()

        // Adapter 설정
        val adapter = FoodItemAdapter(foodItemList)
        recyclerView.adapter = adapter

        mcReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val foodItem = postSnapshot.getValue(FoodItem::class.java)

                    // 읽어온 데이터를 리스트에 추가
                    foodItem?.let {
                        foodItemList.add(it)
                    }
                }
                // Adapter에 데이터 변경을 알림
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터를 읽어오지 못했을 때의 처리
            }
        })
    }
}
