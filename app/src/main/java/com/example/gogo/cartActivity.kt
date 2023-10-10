package com.example.gogo

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gogo.databinding.ActivityCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class cartActivity : ComponentActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var foodItemList: ArrayList<FoodItem>
    private lateinit var keyList: ArrayList<String>

    private fun moveDataToNewReference(
        mcReference: DatabaseReference,
        newReference: DatabaseReference,
        foodItemList: ArrayList<FoodItem>,
        keyList: ArrayList<String>,
        adapter: FoodItemAdapter
    ) {
        mcReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val foodItem = postSnapshot.getValue(FoodItem::class.java)
                    val key = postSnapshot.key

                    foodItem?.let {
                        foodItemList.add(it)
                        keyList.add(key!!)
                        newReference.child(key!!).setValue(it)
                    }
                }
                mcReference.removeValue()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        val databasePath = "cart/$uid"
        val mcReference: DatabaseReference = database.getReference(databasePath)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        foodItemList = ArrayList()
        keyList = ArrayList()

        val adapter = FoodItemAdapter(foodItemList, keyList, mcReference)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        mcReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val foodItem = postSnapshot.getValue(FoodItem::class.java)
                    val key = postSnapshot.key

                    foodItem?.let {
                        foodItemList.add(it)
                        keyList.add(key!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        binding.confirmButton.setOnClickListener() {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.popup_warning)
            val closewarning = dialog.findViewById<Button>(R.id.close_warning_button)
            val realconfirm = dialog.findViewById<Button>(R.id.real_confirm_button)

            realconfirm.setOnClickListener(){
                val currentUser = FirebaseAuth.getInstance().currentUser
                val uid = currentUser?.uid
                val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
                val databasePath1 = "$today/$uid"
                val newReference: DatabaseReference = database.getReference(databasePath1)

                moveDataToNewReference(mcReference, newReference, foodItemList, keyList, adapter)
                dialog.dismiss()
            }

            closewarning.setOnClickListener {
                dialog.dismiss()
            }
            val window: Window? = dialog.window
            window?.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window?.setGravity(Gravity.CENTER)
            dialog.show()
        }
    }
}
