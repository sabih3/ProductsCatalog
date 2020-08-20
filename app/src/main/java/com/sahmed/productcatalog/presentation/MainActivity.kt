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

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var filterScreen : FilterScreen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.factory().create(this).inject(this)

        observeData()
        setupFilters()
    }

    private fun setupFilters() {

        icon_filter.setOnClickListener {
            filterScreen = FilterScreen()
            filterScreen.show(supportFragmentManager,"")
        }
    }

    private fun observeData() {
        mainViewModel.getCatalog()
        mainViewModel.mappedData.observe(this,Observer<Map<String, List<Product>>> {

            val groupedData = it

            val adapter = TabsAdapter(supportFragmentManager,lifecycle)
            var list = mutableListOf<FragmentContainer>()

            it.keys.forEach{

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

        })
    }

    override fun onFiltersApplied(map: HashMap<String, String>,productList:List<Product>) {
        mainViewModel.filterData(map,productList)
    }

    override fun onFiltersCleared() {

    }
}