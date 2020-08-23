package com.sahmed.productcatalog.framework.network

import com.sahmed.productcatalog.framework.network.dto.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CatalogNetworkRepository @Inject constructor(val networkDataSource: NetworkDataSource) {

    fun getProducts(callback:CatalogDataCallback){

        val callable = networkDataSource.getProducts()
        var data : Map<String,List<Product>> = LinkedHashMap()
        var listOfProducts  = listOf<Product>()
        callable.enqueue(object: Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                if(t is UnknownHostException || t is SocketTimeoutException){
                    callback.onError("Seems like you are not connected to Internet",501)
                }else{
                    callback.onError("Something went wrong, Please try again ",502)
                }
            }

            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        data = response.body()!!.groupBy {
                            it.brand!!
                        }
                        listOfProducts = response.body()!!

                        callback.onCatalogFetched(data,listOfProducts)

                    }else{
                        //Empty Data
                        callback.onCatalogFetched(data, listOfProducts)
                    }
                }else{

                    //API Error
                    callback.onError("Something went wrong, Please try again ",500)
                }
            }

        })


    }

    interface CatalogDataCallback{
        fun onCatalogFetched(
            catalogData: Map<String, List<Product>>,
            body: List<Product>
        )
        fun onError(message:String,code:Int)
    }
}