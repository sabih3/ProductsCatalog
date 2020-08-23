package com.sahmed.productcatalog.presentation.main_navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sahmed.productcatalog.presentation.product_list.ProductListFragment

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