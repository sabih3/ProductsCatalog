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
        map: HashMap<String, String>, chosenList: List<Product>) {

//        var chosenSet = chosenList.toSet() // Products with Chosen (Filtration) Attributes
//        var of = setOf<Product>()
//
//        var masterSet = mutableSetOf<Product>()
//        masterSet.addAll(productsList)
//
//
//        val unwantedSet = masterSet.minus(chosenSet) // Subtracted Unwanted Products
//
//        //val filteredSet = masterSet.minus(unwantedSet)
//
//        val filteredSet = masterSet.filter {// Filtered Set
//            it.equals((chosenSet as LinkedHashSet).toArray()[0])
//        }
//        if(filteredSet.equals(masterSet)){
//            //No Filtration Happened
//            mappedData.value = masterSet.groupBy {
//                it.brand!!
//            }
//        }else{
//            mappedData.value = filteredSet.groupBy {
//                it.brand!!
//            }
//        }


        var gson = Gson()
        val arrayTutorialType = object : TypeToken<ArrayList<Product>>() {}.type
        val string = gson.toJson(productsList,arrayTutorialType)
        var typeRef: TypeRef<ArrayList<Product>> = object : TypeRef<ArrayList<Product>>() {

        }


        val conf = Configuration.setDefaults(object : Configuration.Defaults {
            private val jsonProvider: JsonProvider = GsonJsonProvider()
            private val mappingProvider: MappingProvider = GsonMappingProvider()


            override fun jsonProvider(): JsonProvider? {
                return jsonProvider
            }

            override fun mappingProvider(): MappingProvider {
                return mappingProvider
            }


            override fun options(): Set<Option?>? {
                return EnumSet.noneOf(Option::class.java)
            }
        })

        var root = "\$"
        var start = "\$..[?"
        var apple = "(@.brand=='Apple')"
        var ericcson = "(@.brand=='Ericsson')"
        var or = "||"
        var and = "&&"
        var end = "]"
        var gps = "(@.gps==Yes with A-GPS)"
        var audioJack = "(@.audioJack==Yes)"


        var query = start+apple+and+gps+and+audioJack+end

        JsonPath.parse(string).read("\$..[?((@.brand =='Apple' || @.brand=='Ericsson') && @.audioJack=='Yes')]",typeRef)
        JsonPath.parse(string).read(start+apple +end,typeRef)
    }



}