package com.sahmed.productcatalog.presentation.product_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.sahmed.productcatalog.R
import com.sahmed.productcatalog.framework.network.dto.Product
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    var data = mutableListOf<Product>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            ProductsAdapter()
        adapter.data = data

        rv_products.let {
            it.adapter = adapter
        }
    }





}