package com.example.gogo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gogo.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.registerBtn.setOnClickListener {
            EmailRegister()
        }

    }

    private fun EmailRegister(){
        if(binding.emailReg.text.toString().isNullOrEmpty() || binding.passwordReg.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "이메일 혹은 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
        }else{
            signup()
        }
    }

    fun signup(){
        auth.createUserWithEmailAndPassword(binding.emailReg.text.toString(), binding.passwordReg.text.toString())
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    // email로 성공적으로 계정을 만들었을 경우
                    Toast.makeText(this, "계정이 생성되었습니다",Toast.LENGTH_SHORT).show()
                    moveMainPage(task.result?.user)
                }else if(task.exception?.message.isNullOrEmpty()){
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }else{
                    signinEmail()
                }
            }
    }
    fun signinEmail(){
        auth.signInWithEmailAndPassword(binding.emailReg.text.toString(), binding.passwordReg.text.toString())
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    // login 성공
                    Toast.makeText(this, "존재하는 회원입니다. 로그인 하겠습니다.",Toast.LENGTH_SHORT).show()
                    moveMainPage(task.result?.user)
                }else{
                    Toast.makeText(this, "이메일 혹은 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun moveMainPage(user : FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        finish() // 현재 액티비티를 종료합니다.
    }
}