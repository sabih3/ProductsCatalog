package com.sahmed.productcatalog.presentation.main_navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahmed.productcatalog.framework.network.CatalogNetworkRepository
import com.sahmed.productcatalog.framework.network.dto.Product
import com.sahmed.productcatalog.framework.utils.FilteringHelper
import javax.inject.Inject


class MainViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var repository:CatalogNetworkRepository

    private val dataState  = MutableLiveData<ResponseState>()
    val observatoryData :LiveData<ResponseState> = dataState
    private var productsList = mutableListOf<Product>() // Used for caching for filtering

    sealed class ResponseState{
        object Loading: ResponseState()
        data class Success(val data:Map<String,List<Product>>) : ResponseState()
        object Empty:ResponseState()
        data class Error(val message:String): ResponseState()
    }


    fun getCatalog(){
        setResult(ResponseState.Loading)
        repository.getProducts(object: CatalogNetworkRepository.CatalogDataCallback {
            override fun onCatalogFetched(
                catalogData: Map<String, List<Product>>,
                listOfProducts: List<Product>
            ) {
                if(catalogData.isEmpty()){ // Data can be empty returned by repo due to grouping edge case
                    setResult(ResponseState.Empty)
                }else{
                    setResult(ResponseState.Success(catalogData))
                    productsList = listOfProducts.toMutableList()
                }

            }

            override fun onError(message: String, code: Int) {
                setResult(ResponseState.Error(message))
            }

        })

    }
    fun setResult(state:ResponseState){
        dataState.value = state
    }
    fun filterData(
        queryList: MutableList<String>?,
        priceMinSelected: Int,
        priceMaxSelected: Int
    ) {
        val filteredData = FilteringHelper.performFiltering(
            productsList, queryList!!.toMutableList(),
            priceMinSelected, priceMaxSelected
        )
        setResult(ResponseState.Success(filteredData))// To refresh UI with Empty State
        if(filteredData.isEmpty()){
            setResult(ResponseState.Empty) // To show Empty Message
        }

    }

    fun clearFilteredData(){
        getCatalog()
    }
    fun performSearch(query:String){

        val searchedByKeywordData = productsList.filter {
            it.phone!!.contains(query, true)
        }.groupBy {
            it.brand!!
        }

        setResult(ResponseState.Success(searchedByKeywordData))// To refresh UI with Empty State
        if(searchedByKeywordData.isEmpty()){
            setResult(ResponseState.Empty) // To show Empty Message
        }


    }
}