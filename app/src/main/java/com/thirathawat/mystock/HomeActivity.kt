package com.thirathawat.mystock

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.thirathawat.mystock.adapters.SectionsPagerAdapter
import com.thirathawat.mystock.databinding.ActivityHomeBinding
import com.thirathawat.mystock.databinding.CustomTabMenuBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, lifecycle)
        setupViewPager()
        setupWidget()
        setupTab()
    }

    private fun setupTab() {
        TabLayoutMediator(
            binding.tabs,
            binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                val binding = CustomTabMenuBinding.inflate(layoutInflater)
                tab.customView = binding.root
                binding.iconTab.setImageResource(sectionsPagerAdapter.tabIcons[position])
                binding.textTab.text = sectionsPagerAdapter.tabTexts[position]
            }).attach()
    }

    private fun setupWidget() {
        binding.fab.setOnClickListener {
            Intent(applicationContext, CreateActivity::class.java).run {startActivity(this)}
        }

    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = sectionsPagerAdapter

        }.also {
            it.setPageTransformer(HorizontalFlipTransformation())
            it.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 0) {
                        binding.fab.visibility = View.INVISIBLE
                    } else {
                        binding.fab.visibility = View.VISIBLE
                    }
                }
            })
        }
    }
}