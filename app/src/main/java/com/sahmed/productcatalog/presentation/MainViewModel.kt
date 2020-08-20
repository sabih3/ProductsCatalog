package com.sahmed.productcatalog.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahmed.productcatalog.framework.network.CatalogNetworkRepository
import com.sahmed.productcatalog.framework.network.dto.Product
import javax.inject.Inject

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
        map: HashMap<String, String>, chosenList: List<Product>) {

        var chosenSet = chosenList.toSet() // Products with Chosen (Filtration) Attributes

        var masterSet = productsList.toSet() // Whole Inventory

        val unwantedSet = masterSet.minus(chosenSet) // Subtracted Unwanted Products

        val filteredSet = masterSet.minus(unwantedSet) // Filtered Set

        if(unwantedSet.equals(masterSet)){
            //No Filtration Happened
            mappedData.value = masterSet.groupBy {
                it.brand!!
            }
        }else{
            mappedData.value = filteredSet.groupBy {
                it.brand!!
            }
        }


    }



}