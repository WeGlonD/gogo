package com.example.gogo.mcfrags

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.gogo.FoodItem
import com.example.gogo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class mc_fragment1 : Fragment() {
    private lateinit var mcburger01ImageButton: ImageButton
    private val foodItem: FoodItem by lazy { FoodItem("", 0, 0, 0, 0, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mc_a, container, false)
        val button = view.findViewById<Button>(R.id.gotofirst)
        button.setOnClickListener {
            activity?.finish()
        }
        val floatingButton = view.findViewById<FloatingActionButton>(R.id.floatingbutton)
        floatingButton.setOnClickListener {
            showTrayDialog(R.layout.popup_tray)
        }
        val mcBurger01 = view.findViewById<ImageButton>(R.id.mcburger01)
        mcBurger01.setOnClickListener {
            showCustomDialog(R.layout.popup_mc_a)
        }
        return view
    }
    private fun showCustomDialog(dialogLayoutId: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(dialogLayoutId, null)

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER
        }
        dialog.window?.attributes = layoutParams

        val closeButton = dialogView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        val addButton = dialogView.findViewById<Button>(R.id.addbutton)
        addButton.setOnClickListener {
            val bigMacNameTextView = dialogView.findViewById<TextView>(R.id.bigmac_name)
            val bigMacCalTextView = dialogView.findViewById<TextView>(R.id.bigmac_cal)
            val bigMacChTextView = dialogView.findViewById<TextView>(R.id.bigmac_ch)
            val bigMacPrTextView = dialogView.findViewById<TextView>(R.id.bigmac_pr)
            val bigMacFtTextView = dialogView.findViewById<TextView>(R.id.bigmac_ft)
            val bigMacNtTextView = dialogView.findViewById<TextView>(R.id.bigmac_nt)

            val nameValue = bigMacNameTextView.text
            val calValue = bigMacCalTextView.text.toString().toInt()
            val chValue = bigMacChTextView.text.toString().toInt()
            val prValue = bigMacPrTextView.text.toString().toInt()
            val ftValue = bigMacFtTextView.text.toString().toInt()
            val ntValue = bigMacNtTextView.text.toString().toInt()

            if (foodItem.name.isEmpty()) {
                foodItem.name += nameValue
            } else {
                foodItem.name += " $nameValue"
            }
            foodItem.calories += calValue
            foodItem.carbohydrates += chValue
            foodItem.protein += prValue
            foodItem.fat += ftValue
            foodItem.natrium += ntValue
        }
        dialog.show()
    }
    private fun showTrayDialog(dialogLayoutId: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(dialogLayoutId, null)

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER
        }
        dialog.window?.attributes = layoutParams

        val closeButton = dialogView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        val namesTextView = dialogView.findViewById<TextView>(R.id.namesTextView)
        namesTextView.text = "선택한 음식: " + foodItem.name

        val caloriesTextView = dialogView.findViewById<TextView>(R.id.caloriesTextView)
        caloriesTextView.text = "칼로리: " + foodItem.calories + "kcal" + " (일일 권장량의 " + (100*foodItem.calories.toFloat()/2500).toInt() + "%)"

        val carbohytratesTextView = dialogView.findViewById<TextView>(R.id.carbohydratesTextView)
        carbohytratesTextView.text = "탄수화물: " + foodItem.carbohydrates + "g" + " (일일 권장량의 " + (100*foodItem.carbohydrates.toFloat()/130).toInt() + "%)"

        val proteinsTextView = dialogView.findViewById<TextView>(R.id.proteinsTextView)
        proteinsTextView.text = "단백질: " + foodItem.protein + "g" + " (일일 권장량의 " + (100*foodItem.protein.toFloat()/55).toInt() + "%)"

        val fatsTextView = dialogView.findViewById<TextView>(R.id.fatsTextView)
        fatsTextView.text = "지방: " + foodItem.fat + "g" + " (일일 권장량의 " + (100*foodItem.fat.toFloat()/51).toInt() + "%)"

        val natriumsTextView = dialogView.findViewById<TextView>(R.id.natriumsTextView)
        natriumsTextView.text = "나트륨: " + foodItem.natrium + "mg" + " (일일 권장량의 " + (100*foodItem.natrium.toFloat()/1500).toInt() + "%)"
        dialog.show()
    }
}
