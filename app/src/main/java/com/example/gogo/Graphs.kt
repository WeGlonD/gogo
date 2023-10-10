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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Graphs : AppCompatActivity() {

    private lateinit var binding : ActivityGraphsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barChart: BarChart = findViewById(R.id.barChart)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        val today = intent.getIntExtra("selectedDate", 0).toString()
        val databaseReference = FirebaseDatabase.getInstance().getReference(today).child(uid!!)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalCalories = 0f
                var totalFat = 0f
                var totalCarbohydrates = 0f
                var totalProtein = 0f
                var totalNatrium = 0f

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

                totalCalories /= 1500
                totalFat /= 51
                totalProtein /= 55
                totalCarbohydrates /= 130
                totalNatrium /= 2000

                totalCalories *= 100
                totalFat *= 100
                totalProtein *= 100
                totalCarbohydrates *= 100
                totalNatrium *= 100

                val entries = mutableListOf<BarEntry>()
                entries.add(BarEntry(0f, totalCalories))
                entries.add(BarEntry(1f, totalFat))
                entries.add(BarEntry(2f, totalCarbohydrates))
                entries.add(BarEntry(3f, totalProtein))
                entries.add(BarEntry(4f, totalNatrium))

                val dataSet = BarDataSet(entries, "일일 권장 섭취량 대비 섭취량(%)")
                dataSet.color = Color.BLACK

                val labels = listOf("칼로리", "지방", "탄수화물", "단백질", "나트륨")

                val data = BarData(dataSet)
                data.barWidth = 0.9f

                barChart.data = data
                /*barChart.setFitBars(true)*/
                barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                barChart.xAxis.granularity = 1f
                barChart.description.isEnabled = false
                barChart.axisLeft.axisMinimum = 0f
                barChart.axisRight.axisMinimum = 0f

                barChart.setDrawGridBackground(false) // Remove grid background
                barChart.xAxis.setDrawGridLines(false) // Remove vertical grid lines
                barChart.axisLeft.setDrawGridLines(false) // Remove horizontal grid lines
                barChart.axisRight.setDrawGridLines(false) // Remove horizontal

                barChart.invalidate()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: ${databaseError.message}")
            }
        })
    }
}

