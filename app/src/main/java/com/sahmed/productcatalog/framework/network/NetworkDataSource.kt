package com.sahmed.productcatalog.framework.network

import com.sahmed.productcatalog.framework.network.dto.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface NetworkDataSource {

    @GET("b/5f3a3fcf4d939910361666fe/latest")
    fun getProducts(): Call<List<Product>>

}