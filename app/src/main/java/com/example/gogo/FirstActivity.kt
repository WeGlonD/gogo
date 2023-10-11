package com.example.gogo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.gogo.databinding.ActivityFirstBinding

class FirstActivity : ComponentActivity() {

    private lateinit var binding : ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button11.setOnClickListener {
            val res = "Mc"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.gotomain.setOnClickListener {
            finish()
        }
    }
}