package com.example.gogo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.gogo.databinding.ActivityUserinfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InfoActivity : ComponentActivity(){
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding : ActivityUserinfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        if (uid != null) {
            val databasePath = "info/$uid"
            databaseReference = FirebaseDatabase.getInstance().getReference(databasePath)
        }
        //정보 있으면 프리징
        databaseReference.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val infoMap = snapshot.value as? Map<String, Map<String, String>>
                val info = infoMap?.values?.firstOrNull()

                if (info != null) {
                    val weight = info["weight"]
                    val height = info["height"]
                    val age = info["age"]
                    val gender = info["gender"]

                    if (weight != null && height != null && age != null && gender != null) {
                        binding.textWeight1.setText(weight)
                        binding.textHeight1.setText(height)
                        binding.textAge1.setText(age)
                        binding.textGender1.setText(gender)

                        binding.textAge1.isEnabled = false
                        binding.textGender1.isEnabled = false
                        binding.textHeight1.isEnabled = false
                        binding.textWeight1.isEnabled = false
                        binding.infoConfirm.text = "수정하기"
                    } else {
                        Toast.makeText(this, "데이터가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "데이터가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Log.e("InfoActivity", "Error getting data", exception)
        }

        binding.infoConfirm.setOnClickListener {
            val weightInput = binding.textWeight1.text.toString()
            val heightInput = binding.textHeight1.text.toString()
            val ageInput = binding.textAge1.text.toString()
            val genderInput = binding.textGender1.text.toString()
            if (binding.infoConfirm.text == "입력 완료") {
                if (weightInput.isNullOrEmpty() || heightInput.isNullOrEmpty() || ageInput.isNullOrEmpty() || genderInput.isNullOrEmpty()) {
                    Toast.makeText(this, "똑바로 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    val weight: Int? = weightInput.toIntOrNull()
                    val height: Int? = heightInput.toIntOrNull()
                    val age: Int? = ageInput.toIntOrNull()
                    if (weight != null && height != null && age != null) {
                        if (genderInput == "M" || genderInput == "F") {
                            val InfoItemRef = databaseReference.push()

                            val infoMap = mapOf(
                                "age" to age.toString(),
                                "gender" to genderInput,
                                "height" to height.toString(),
                                "weight" to weight.toString()
                            )

                            InfoItemRef.setValue(infoMap)

                            binding.infoConfirm.text = "수정하기"

                            binding.textAge1.isEnabled = false
                            binding.textGender1.isEnabled = false
                            binding.textHeight1.isEnabled = false
                            binding.textWeight1.isEnabled = false
                        } else {
                            Toast.makeText(this, "성별을 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "숫자를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                databaseReference.removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show()

                        // EditText 초기화 및 활성화
                        binding.textAge1.text.clear()
                        binding.textGender1.text.clear()
                        binding.textHeight1.text.clear()
                        binding.textWeight1.text.clear()

                        binding.textAge1.isEnabled = true
                        binding.textGender1.isEnabled = true
                        binding.textHeight1.isEnabled = true
                        binding.textWeight1.isEnabled = true

                        binding.infoConfirm.text = "입력 완료"
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "데이터 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}