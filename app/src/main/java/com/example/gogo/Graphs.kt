package com.example.gogo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gogo.databinding.ActivityGraphsBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Graphs : AppCompatActivity() {

    private lateinit var binding: ActivityGraphsBinding
    private lateinit var databaseReference2: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barChart: BarChart = findViewById(R.id.barChart)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        val today = intent.getIntExtra("selectedDate", 0).toString()
        val databaseReference = FirebaseDatabase.getInstance().getReference(today).child(uid!!)

        if (uid != null) {
            val databasePath = "info/$uid"
            databaseReference2 = FirebaseDatabase.getInstance().getReference(databasePath)
        }

        var yourcalories = 2000f
        var yourfat = 67f
        var yourcarbohydrates = 275f
        var yourprotein = 75f

        databaseReference2.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val infoMap = snapshot.value as? Map<String, Map<String, String>>
                val info = infoMap?.values?.firstOrNull()

                val age = info?.get("age")!!.toFloat()
                val gender = info.get("gender")
                val height = info.get("height")!!.toFloat()
                val weight = info.get("weight")!!.toFloat()

                if (gender == "M"){
                    yourcalories = ((66 + (13.7 * weight) + (5 * height) - (6.76 * age))*1.4).toFloat()
                    yourcarbohydrates = (((yourcalories * 55) / 100) / 4)
                    yourprotein = (((yourcalories * 15) / 100) / 4)
                    yourfat = (((yourcalories * 30) / 100) / 9)
                }
                else{
                    yourcalories = ((655 + (9.6 * weight) + (1.8 * height) - (4.7 * age))*1.4).toFloat()
                    yourcarbohydrates = (((yourcalories * 55) / 100) / 4)
                    yourprotein = (((yourcalories * 15) / 100) / 4)
                    yourfat = (((yourcalories * 30) / 100) / 9)
                }
            }
            binding.excesscalories1.text = yourcalories.toString()
            binding.excesscarbohydrates1.text = yourcarbohydrates.toString()
            binding.excessprotein1.text = yourprotein.toString()
            binding.excessfat1.text = yourfat.toString()
            binding.excessnatrium1.text = "2000"

            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var totalCalories = 0f
                    var totalFat = 0f
                    var totalCarbohydrates = 0f
                    var totalProtein = 0f
                    var totalNatrium = 0f

                    var esCalories = 0f
                    var esCarboHydrates = 0f
                    var esProtein = 0f
                    var esFat = 0f
                    var esNatrium = 0f

                    for (itemSnapshot in dataSnapshot.children) {
                        val foodItem = itemSnapshot.getValue(FoodItem::class.java)
                        if (foodItem != null) {
                            totalCalories += foodItem.calories
                            totalFat += foodItem.fat
                            totalCarbohydrates += foodItem.carbohydrates
                            totalProtein += foodItem.protein
                            totalNatrium += foodItem.natrium
                        }
                    }

                    if (totalCalories < yourcalories) {
                        esCalories = yourcalories - totalCalories
                        binding.shortcalories.setText(esCalories.toString())
                    } else {
                        esCalories = totalCalories - yourcalories
                        binding.excesscalories.setText(esCalories.toString())
                    }

                    if (totalCarbohydrates < yourcarbohydrates) {
                        esCarboHydrates = yourcarbohydrates - totalCarbohydrates
                        binding.shortcarbohydrates.setText(esCarboHydrates.toString())
                    } else {
                        esCarboHydrates = totalCarbohydrates - yourcarbohydrates
                        binding.excesscarbohydrates.setText(esCarboHydrates.toString())
                    }

                    if (totalProtein < yourprotein) {
                        esProtein = yourprotein - totalProtein
                        binding.shortprotein.setText(esProtein.toString())
                    } else {
                        esProtein = totalProtein - yourprotein
                        binding.excessprotein.setText(esProtein.toString())
                    }

                    if (totalFat < yourfat) {
                        esFat = yourfat - totalFat
                        binding.shortfat.setText(esFat.toString())
                    } else {
                        esFat = totalFat - yourfat
                        binding.excessfat.setText(esFat.toString())
                    }

                    if (totalNatrium < 2000) {
                        esNatrium = 2000 - totalNatrium
                        binding.shortnatrium.setText(esNatrium.toString())
                    } else {
                        esNatrium = totalNatrium - 2000
                        binding.excessnatrium.setText(esNatrium.toString())
                    }

                    totalCalories /= yourcalories
                    totalFat /= yourfat
                    totalProtein /= yourprotein
                    totalCarbohydrates /= yourcarbohydrates
                    totalNatrium /= 2000

                    totalCalories *= 100
                    totalFat *= 100
                    totalProtein *= 100
                    totalCarbohydrates *= 100
                    totalNatrium *= 100

                    val entries = mutableListOf<BarEntry>()
                    entries.add(BarEntry(0f, totalCalories))
                    entries.add(BarEntry(1f, totalCarbohydrates))
                    entries.add(BarEntry(2f, totalProtein))
                    entries.add(BarEntry(3f, totalFat))
                    entries.add(BarEntry(4f, totalNatrium))

                    val colors = mutableListOf<Int>()
                    for (i in entries.indices) {
                        //부족
                        if (entries[i].y < 100) {
                            colors.add(Color.BLUE)
                        } else {
                            //초과
                            colors.add(Color.RED)
                        }
                    }
                    val dataSet = BarDataSet(entries, "일일 권장 섭취량 대비 섭취량(%)")
                    dataSet.colors = colors

                    val labels = listOf("칼로리", "탄수화물", "단백질", "지방", "나트륨")

                    val data = BarData(dataSet)
                    data.barWidth = 0.9f

                    barChart.data = data
                    barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                    barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                    barChart.xAxis.granularity = 1f
                    barChart.description.isEnabled = false
                    barChart.axisLeft.axisMinimum = 0f
                    barChart.axisLeft.axisMaximum = 100f
                    barChart.axisRight.axisMaximum = 100f
                    barChart.axisRight.axisMinimum = 0f

                    barChart.setDrawGridBackground(false) // Remove grid background
                    barChart.xAxis.setDrawGridLines(false) // Remove vertical grid lines
                    barChart.axisLeft.setDrawGridLines(false) // Remove horizontal grid lines
                    barChart.axisRight.setDrawGridLines(false) // Remove horizontal
                    barChart.setTouchEnabled(false)
                    barChart.invalidate()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Error: ${databaseError.message}")
                }
            })
        }
    }
}

