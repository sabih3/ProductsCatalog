package com.sahmed.productcatalog.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsAdapter(fm:FragmentManager,lifecycle:Lifecycle):FragmentStateAdapter(fm,lifecycle) {

    var list =  listOf<FragmentContainer>()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = list.get(position).fragment as ProductListFragment
        fragment.data = list.get(position).data.toMutableList()
        return fragment
    }
}