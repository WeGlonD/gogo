package com.example.gogo.mcfrags

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gogo.FoodItem
import com.example.gogo.R
import com.example.gogo.cartActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class mc_fragment1 : Fragment() {
    private lateinit var mcburger01ImageButton: ImageButton
    private val foodItem: FoodItem by lazy { FoodItem("", 0, 0, 0, 0, 0) }
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseReference = FirebaseDatabase.getInstance().reference
        val view = inflater.inflate(R.layout.fragment_mc_a, container, false)
        val button = view.findViewById<Button>(R.id.gotofirst)
        button.setOnClickListener {
            activity?.finish()
        }
        val mcBurger01 = view.findViewById<ImageButton>(R.id.mcburger01)
        mcBurger01.setOnClickListener {
            showCustomDialog(R.layout.popup_mc_a)
        }
        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingbutton)
        floatingActionButton.setOnClickListener {
            val intent = Intent(this.requireContext(), cartActivity::class.java)
            startActivity(intent)
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
            }
            if (foodItem.calories == 0) {
                foodItem.calories += calValue
            }
            if (foodItem.carbohydrates == 0) {
                foodItem.carbohydrates += chValue
            }
            if (foodItem.protein == 0){
                foodItem.protein += prValue
            }
            if (foodItem.fat == 0){
                foodItem.fat += ftValue
            }
            if (foodItem.natrium == 0) {
                foodItem.natrium += ntValue
            }

            val currentUser = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid

            if (uid != null) {
                val databasePath = "cart/$uid"
                val foodItemRef = databaseReference.child(databasePath).push()
                foodItemRef.setValue(foodItem)
            }
        }
        dialog.show()
    }
}