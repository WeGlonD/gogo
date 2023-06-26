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
            val intent = Intent(this, McActivity::class.java)
            startActivity(intent)
        }
        binding.gotomain.setOnClickListener {
            finish()
        }
    }
}