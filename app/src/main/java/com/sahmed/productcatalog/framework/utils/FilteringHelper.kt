package com.sahmed.productcatalog.framework.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.Option
import com.jayway.jsonpath.TypeRef
import com.jayway.jsonpath.spi.json.GsonJsonProvider
import com.jayway.jsonpath.spi.json.JsonProvider
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider
import com.jayway.jsonpath.spi.mapper.MappingProvider
import com.sahmed.productcatalog.framework.network.dto.Product
import com.sahmed.productcatalog.presentation.filter.FilterScreen
import java.util.*
import kotlin.collections.ArrayList

object FilteringHelper {

    const val LOOKUP_APPLE = "@.brand=='Apple'"
    const val LOOKUP_ERICSSON = "@.brand=='Ericsson'"
    const val LOOKUP_GPS = "@.gps!='No'"
    const val LOOKUP_AUDIOJACK = "@.audioJack=='Yes'"
    const val LOOKUP_NO_SIM = "@.sim=='No'"
    const val LOOKUP_SINGLE_SIM = "@.sim=='Single SIM'"
    const val LOOKUP_MINI_SIM = "@.sim=='Mini-SIM'"
    const val LOOKUP_MICRO_SIM = "@.sim=='Micro-SIM'"
    const val LOOKUP_NANO_SIM = "@.sim=='Nano-SIM eSIM'"
    const val LOOKUP_E_SIM = "@.sim=='eSIM'"
    const val LOOKUP_PRICE_MIN = "@.priceEur >="
    const val LOOKUP_PRICE_MAX = "@.priceEur <="
    init {
        setConfig()
    }

    val gson = Gson()
    val root = "\$" // Returns all nodes in the array
    val start = "\$..[?("
    val or = "||"
    val and = "&&"
    val end = ")]"

    val typeRef : TypeRef<ArrayList<Product>> = object : TypeRef<ArrayList<Product>>() {
    }
    val listType = object : TypeToken<ArrayList<Product>>() {}.type

    fun performFiltering(
        listOfProducts: List<Product>,
        queryList: MutableList<String>,
        priceMinSelected: Int,
        priceMaxSelected: Int
    ):Map<String, List<Product>> {


        val string = gson.toJson(listOfProducts,listType) // Obtaining Json String of products list

        var query = root // Query Default set to to return all Products
        var priceList = mutableListOf<String>()

        //Selected Prices were kept separated from querylist to retain the pre selected state in Filter Sheet
        if(priceMinSelected!= FilterScreen.DEFAULT_MIN_VALUE || priceMaxSelected!= FilterScreen.DEFAULT_MAX_VALUE){
            if(priceMinSelected!= FilterScreen.DEFAULT_MIN_VALUE){
                priceList.add(LOOKUP_PRICE_MIN + " " +priceMinSelected)

            }
            if(priceMaxSelected!= FilterScreen.DEFAULT_MAX_VALUE){
                priceList.add(LOOKUP_PRICE_MAX+ " "+ priceMaxSelected)
            }
        }

        //In case all the brands are selected, we need to OR them before combining with other filters,
        //that's the reason, here we are removing their individual values, and adding them after combining
        //with OR, eg: To get ((@.brand=='Apple' || @.brand=='Ericsson')&&@.sim=='Mini-SIM')
        //this should give any phone among Apple and Ericsson with Mini-Sim
        if(queryList.isNotEmpty() && queryList.contains(LOOKUP_APPLE) && queryList.contains( LOOKUP_ERICSSON)){
            queryList.remove(LOOKUP_APPLE)
            queryList.remove(LOOKUP_ERICSSON)
            queryList.add("("+ LOOKUP_APPLE+or+" "+ LOOKUP_ERICSSON+")")

        }

        /** Building Query**/
        if(queryList.size>0){
            query=start // if query params present, replace the default with Query start

            queryList.forEachIndexed{index,element->
                query+= element

                if(index != queryList.size-1){ // To Avoid appending operator after last token
                    if(priceList.size!=0){ //When price range comes along other filters, it will be OR among other filters
                        query+=or
                    }else{
                        query+= and   // in case no price range, only filters, they will be AND among each other
                                      // because a phone can have one feature at a time
                    }
                }
            }
            if(priceList.size>0){ // Price is being ANDed among its own min and max values 'min>=50 && max<=800'

                priceList.forEachIndexed{index,element->
                    query+=and
                    query+= element
                }
            }
            query+=end

         //Other filters are un touched, Only price ranges are selected
        }else if(queryList.size==0 && priceList.size!=0){ // only & only price range selected
            query=start

            priceList.forEachIndexed{index,element->
                query+=element
                if(index != priceList.size-1)query+= and // Price is a range, hence it will be anded &&
            }
            query+= end
        }else{
            query = root
        }
        /** Query Building End**/

        // Initializing the resultant data with master data for safe side, in case of exception
        var filteredList = listOfProducts

        try{
            filteredList = JsonPath.parse(string).read(query, typeRef) // Providing Query to parser
        }catch (exc:Exception){
            // To prevent any crash, returning same products list
            return filteredList.groupBy { it.brand!! }

        }
        return filteredList.groupBy {
            it.brand!!
        }

    }

    /** Using GsonJsonProvider as JsonProvider &&
     *  GsonMappingProvider as Mapping Provider
     *  Because of typeRef which is required in JsonPath.parse().
     *  Usage of TypeRef is only supported when overriding
     *  the default Config to use above mentioned Providers
     */
    private fun setConfig(){
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
    }

}