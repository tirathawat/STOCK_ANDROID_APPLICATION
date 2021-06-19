package com.thirathawat.mystock.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thirathawat.mystock.HomeActivity
import com.thirathawat.mystock.ProductFragment
import com.thirathawat.mystock.R
import com.thirathawat.mystock.StockFragment

class SectionsPagerAdapter(context: HomeActivity, fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    val tabIcons: Array<Int> = arrayOf(R.drawable.ic_product, R.drawable.ic_stock)
    val tabTexts: Array<String> = arrayOf(*context.resources.getStringArray(R.array.tab_title))

    override fun getItemCount(): Int = tabIcons.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductFragment()
            else -> StockFragment()
        }
    }
}