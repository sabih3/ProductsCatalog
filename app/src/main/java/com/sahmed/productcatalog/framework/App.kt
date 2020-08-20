package com.sahmed.productcatalog.framework

import android.app.Application
import com.sahmed.productcatalog.framework.di.AppComponent
import com.sahmed.productcatalog.framework.di.DaggerAppComponent
import com.sahmed.productcatalog.framework.network.NetworkDataSource
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var services:NetworkDataSource

    val appComponent:AppComponent  by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}