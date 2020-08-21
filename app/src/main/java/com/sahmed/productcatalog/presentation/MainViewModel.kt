package com.sahmed.productcatalog.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.Option
import com.jayway.jsonpath.TypeRef
import com.jayway.jsonpath.spi.json.GsonJsonProvider
import com.jayway.jsonpath.spi.json.JacksonJsonProvider
import com.jayway.jsonpath.spi.json.JsonProvider
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider
import com.jayway.jsonpath.spi.mapper.MappingProvider
import com.sahmed.productcatalog.framework.network.CatalogNetworkRepository
import com.sahmed.productcatalog.framework.network.dto.Product
import com.sahmed.productcatalog.framework.utils.FilteringHelper
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var repository:CatalogNetworkRepository

    val mappedData  = MutableLiveData<Map<String,List<Product>>>()
    lateinit var data : Map<String,List<Product>>
    var productsList = mutableListOf<Product>()

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
        queryList:MutableList<String>?) {

        mappedData.value = FilteringHelper.performFiltering(productsList,queryList!!.toMutableList())

    }
}