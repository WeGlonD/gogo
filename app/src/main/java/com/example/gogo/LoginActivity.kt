package com.example.gogo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gogo.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.emailLoginBtn.setOnClickListener {
            email_login()
        }
        binding.regBtn.setOnClickListener {
            Register()
        }

    }

    private fun email_login(){
        if(binding.emailEdt.text.toString().isNullOrEmpty() || binding.passwordEdt.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "이메일 혹은 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
        }else{
            signin()
        }
    }

    fun signin(){
        auth.signInWithEmailAndPassword(binding.emailEdt.text.toString(), binding.passwordEdt.text.toString())
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    // email로 로그인
                    moveMainPage(task.result?.user)
                }else if(task.exception?.message.isNullOrEmpty()){
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this, "이메일 혹은 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun Register(){
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
    fun moveMainPage(user : FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }
}


