package com.sahmed.productcatalog.framework

import androidx.lifecycle.AndroidViewModel

open class AppViewModel(application:App):AndroidViewModel(application) {
    val application = application
}