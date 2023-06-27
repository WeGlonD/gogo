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
import com.example.gogo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class mc_fragment1 : Fragment() {
    private lateinit var mcburger01ImageButton: ImageButton // 변수 선언

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
            showCustomDialog(R.layout.popup_tray)
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
        dialog.show()
    }
}



