package com.example.gogo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gogo.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화 코드
        FirebaseApp.initializeApp(this) // Firebase 앱을 초기화

        // Firebase 인증 초기화
        auth = FirebaseAuth.getInstance()
        binding.button01.setOnClickListener {
            val intent = Intent(this, PrimeActivity::class.java)
            startActivity(intent)
        }

        binding.button06.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }

        binding.button02.setOnClickListener {
            val intent = Intent(this, cartActivity::class.java)
            startActivity(intent)
        }
        binding.button03.setOnClickListener {
            auth = Firebase.auth
            auth.signOut();
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.button04.setOnClickListener {
            val intent = Intent(this, Calender::class.java)
            startActivity(intent)
        }
        binding.button05.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }
}