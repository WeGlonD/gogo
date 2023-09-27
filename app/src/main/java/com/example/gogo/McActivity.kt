package com.example.gogo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.gogo.databinding.ActivityMcBinding
import com.example.gogo.mcfrags.mc_fragment1
import com.example.gogo.mcfrags.mc_fragment2
import com.example.gogo.mcfrags.mc_fragment3
import com.example.gogo.mcfrags.mc_fragment4
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class McActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        val adapter = MyPagerAdapter(this)
        viewPager.adapter = adapter

        val tabTitles = listOf("버거", "맥모닝", "사이드", "맥카페")

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private inner class MyPagerAdapter(activity: FragmentActivity) :
        FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return 4 // 탭의 개수를 지정하세요.
        }//dldslajflaja

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> mc_fragment1()
                1 -> mc_fragment2()
                2 -> mc_fragment3()
                3 -> mc_fragment4()
                else -> throw IllegalArgumentException("Invalid tab position: $position")
            }
        }
    }
}
