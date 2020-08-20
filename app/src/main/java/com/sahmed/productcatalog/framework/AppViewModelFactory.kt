package com.sahmed.productcatalog.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sahmed.productcatalog.framework.di.DaggerAppComponent
import java.lang.IllegalStateException
import javax.inject.Inject

class AppViewModelFactory @Inject constructor(val application: Application):ViewModelProvider.Factory {



    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(AppViewModel::class.java.isAssignableFrom(modelClass)){
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }else{
            throw IllegalStateException("View model must extend AppViewModel.kt")
        }
    }
}