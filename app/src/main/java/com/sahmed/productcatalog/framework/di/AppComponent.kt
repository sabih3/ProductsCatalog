package com.sahmed.productcatalog.framework.di

import android.content.Context
import com.sahmed.productcatalog.framework.App
import com.sahmed.productcatalog.framework.network.RestClient
import com.sahmed.productcatalog.presentation.main_navigation.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [RestClient::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context):AppComponent
    }


    fun inject(activity : MainActivity)
    fun inject(app: App)
}