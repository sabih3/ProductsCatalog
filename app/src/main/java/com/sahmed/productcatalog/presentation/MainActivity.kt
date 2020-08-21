package com.sahmed.productcatalog.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.sahmed.productcatalog.R
import com.sahmed.productcatalog.framework.di.DaggerAppComponent
import com.sahmed.productcatalog.framework.network.dto.Product
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),FilterScreen.FilterInterface {

    // List for Applied filters, will be utilized to pass again in Filter screen to show pre
    //selected check boxes
    private var appliedFilters = mutableListOf<String>()

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var filterScreen : FilterScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.factory().create(this).inject(this)

        observeData()
        setupFilters()
        setupSearch()
    }

    private fun observeData() {
        mainViewModel.getCatalog()
        mainViewModel.mappedData.observe(this,Observer<Map<String, List<Product>>> { groupedData->
            setupPager(groupedData)
        })
    }

    private fun setupFilters() {

        icon_filter.setOnClickListener {
            filterScreen = FilterScreen()
            if(appliedFilters.isNotEmpty())filterScreen.queryList = appliedFilters
            filterScreen.show(supportFragmentManager,"")
        }
    }

    private fun setupSearch() {
        btn_open_search.setOnClickListener {

        }
    }



    /**Fun set up view pager and tabs according to data grouped by brand
     *
     */
    private fun setupPager(groupedData: Map<String, List<Product>>) {
        val adapter = TabsAdapter(supportFragmentManager,lifecycle)
        var list = mutableListOf<FragmentContainer>()

        groupedData.keys.forEach{
            var container  = FragmentContainer(ProductListFragment(),it,groupedData.get(it)!!)
            list.add(container)
        }
        pager.let {
            adapter.list = list
            it.adapter = adapter
        }

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = list.get(position).title
        }.attach()
    }

    //Filter Screen/FilterInterface
    override fun onFiltersApplied(
        queryList: MutableList<String>?
    ) {
        appliedFilters = queryList!!
        mainViewModel.filterData(appliedFilters)
    }

    override fun onFiltersCleared() {
        mainViewModel.getCatalog()
    }
}