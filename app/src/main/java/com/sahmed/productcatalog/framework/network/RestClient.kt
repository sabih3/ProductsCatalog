package com.sahmed.productcatalog.framework.network

import com.sahmed.productcatalog.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RestClient {
    val KEY_HEADER = "secret-key"

    @Provides
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    @Provides
    fun getClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()

                val request = original.newBuilder()
                val build = request.addHeader(KEY_HEADER, BuildConfig.KEY)
                    .method(original.method, original.body)
                    .build()

                return chain.proceed(build)
            }

        })
        if(BuildConfig.DEBUG){
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(loggingInterceptor)
        }

        return client.build()

    }

    @Provides
    fun provideRestClient(): NetworkDataSource {
        return provideRetrofit().create(NetworkDataSource::class.java)
    }
}