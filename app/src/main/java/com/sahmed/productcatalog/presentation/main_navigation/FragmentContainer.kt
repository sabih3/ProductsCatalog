package com.sahmed.productcatalog.presentation.main_navigation

import androidx.fragment.app.Fragment
import com.sahmed.productcatalog.framework.network.dto.Product

data class FragmentContainer(val fragment:Fragment,val title:String,val data:List<Product>)
