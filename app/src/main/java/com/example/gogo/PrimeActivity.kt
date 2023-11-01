package com.example.gogo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.gogo.databinding.ActivityFirstBinding
import com.example.gogo.databinding.ActivityPrimeBinding

class PrimeActivity : ComponentActivity() {

    private lateinit var binding : ActivityPrimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menu11.setOnClickListener {
            val res = "채소"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu12.setOnClickListener {
            val res = "과일"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu13.setOnClickListener {
            val res = "곡물"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu21.setOnClickListener {
            val res = "버섯"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu22.setOnClickListener {
            val res = "육류"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu23.setOnClickListener {
            val res = "생선"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu31.setOnClickListener {
            val res = "해조류"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu32.setOnClickListener {
            val res = "해산물"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu33.setOnClickListener {
            val res = "견과류"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu41.setOnClickListener {
            val res = "유제품"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu42.setOnClickListener {
            val res = "조미료"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu43.setOnClickListener {
            val res = "양념"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu51.setOnClickListener {
            val res = "가공식품"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu52.setOnClickListener {
            val res = "음료"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
        binding.menu53.setOnClickListener {
            val res = "라면"
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("restaurant",res)
            startActivity(intent)
        }
    }
}