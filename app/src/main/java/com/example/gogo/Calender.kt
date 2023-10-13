package com.example.gogo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gogo.databinding.ActivityCalenderBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class Calender : AppCompatActivity() {

    lateinit var binding: ActivityCalenderBinding

    lateinit var calendarView: CalendarView
    lateinit var today: TextView

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        calendarView = binding.calendarView

        val formatter: DateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val date = Date(calendarView.getDate())
        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val day: Int = (year * 10000) + ((month + 1) * 100) + dayOfMonth
            val intent = Intent(this, Graphs::class.java)
            intent.putExtra("selectedDate", day)
            startActivity(intent)
        })
    }
}