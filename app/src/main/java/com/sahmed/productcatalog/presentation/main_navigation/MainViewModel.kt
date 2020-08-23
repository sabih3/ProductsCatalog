package com.sahmed.productcatalog.presentation.main_navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahmed.productcatalog.framework.network.CatalogNetworkRepository
import com.sahmed.productcatalog.framework.network.dto.Product
import com.sahmed.productcatalog.framework.utils.FilteringHelper
import javax.inject.Inject


class MainViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var repository:CatalogNetworkRepository

    val mappedData  = MutableLiveData<Map<String,List<Product>>>()
    private lateinit var data : Map<String,List<Product>>
    private var productsList = mutableListOf<Product>()

    fun getCatalog(){
        repository.getProducts(object: CatalogNetworkRepository.CatalogDataCallback {
            override fun onCatalogFetched(
                catalogData: Map<String, List<Product>>,
                listOfProducts: List<Product>
            ) {
                mappedData.value = catalogData
                data = catalogData
                productsList = listOfProducts.toMutableList()
            }

            override fun onError(message: String, code: Int) {
                TODO("Not yet implemented")
            }

        })

    }

    fun filterData(
        queryList: MutableList<String>?,
        priceMinSelected: Int,
        priceMaxSelected: Int
    ) {
        mappedData.value = FilteringHelper.performFiltering(productsList,queryList!!.toMutableList(),
                            priceMinSelected,priceMaxSelected)

    }

    fun clearFilteredData(){
        getCatalog()
    }
    fun performSearch(query:String){
        mappedData.value = productsList.filter {
            it.phone!!.contains(query,true)
        }.groupBy {
            it.brand!!
        }
    }
}