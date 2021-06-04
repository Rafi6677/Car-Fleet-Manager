package com.example.carfleetmanager.presentation.cars.carlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.ActivityCarsBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import com.example.carfleetmanager.presentation.addnewcar.AddNewCarActivity
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsActivity : AppCompatActivity() {

    val viewModel by viewModels<CarFleetViewModel>()
    private lateinit var binding: ActivityCarsBinding
    private lateinit var viewPagerAdapter: CarListViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        initButtons()
    }

    private fun initViewPager() {
        val fragmentManager = supportFragmentManager
        viewPagerAdapter = CarListViewPagerAdapter(fragmentManager, lifecycle)
        binding.carListViewPager.adapter = viewPagerAdapter

        binding.carListTabLayout.apply {
            addTab(this.newTab().setText(resources.getString(R.string.car_list)))
            addTab(this.newTab().setText(resources.getString(R.string.map)))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.carListViewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

        binding.carListViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.carListTabLayout.apply {
                    selectTab(this.getTabAt(position))
                }
            }
        })
    }

    private fun initButtons() {
        binding.addNewCarButton.setOnClickListener {
            val intent = Intent(this, AddNewCarActivity::class.java)
            startActivity(intent)
        }
    }


}