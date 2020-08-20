package com.sahmed.productcatalog.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sahmed.productcatalog.R
import com.sahmed.productcatalog.framework.network.dto.Product
import kotlinx.android.synthetic.main.filter_bottom_sheet.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FilterScreen : BottomSheetDialogFragment(),CompoundButton.OnCheckedChangeListener{

    lateinit var listener : FilterInterface
    var filterMap = HashMap<String,String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FilterInterface){
            listener = context
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.filter_bottom_sheet, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cb_apple.setOnCheckedChangeListener(this)
        cb_erricson.setOnCheckedChangeListener(this)
        cb_audio.setOnCheckedChangeListener(this)
        cb_gps.setOnCheckedChangeListener(this)
        cb_sim.setOnCheckedChangeListener(this)

        apply_btn.setOnClickListener {
            if(appleProduct.isSelected){
                listOfProduct.add(appleProduct)
            }

            if(ericssonProduct.isSelected){
                listOfProduct.add(ericssonProduct)
            }
            listener.onFiltersApplied(filterMap,listOfProduct)
            this@FilterScreen.dismiss()
        }


    }


    interface FilterInterface{
        fun onFiltersApplied(map:HashMap<String,String>,listOfProducts:List<Product>)
        fun onFiltersCleared()
    }

    var listOfProduct = mutableListOf<Product>()
    var appleProduct = Product(brand = "Apple")
    var ericssonProduct = Product(brand = "Ericsson")
    var individualProduct = Product()
    override fun onCheckedChanged(check_box: CompoundButton?, checked: Boolean) {

        when(checked){
            true->{


                when(check_box!!.id){

                    cb_apple.id ->{
                        //filterMap.put("brand",cb_apple.text.toString())
                        var product = Product(brand = cb_apple.text.toString())
                        appleProduct.isSelected = true
                        //listOfProduct.add(product)
                        //listOfProduct.add(appleProduct)
                    }

                    cb_erricson.id ->{
                        //filterMap.put("brand",cb_erricson.text.toString())
                        var product = Product(brand = cb_erricson.text.toString())
                        ericssonProduct.isSelected = true
                        //listOfProduct.add(ericssonProduct)
                        //listOfProduct.add(product)
                    }

                    cb_audio.id ->{
                        //filterMap.put("audioJack","Yes")
                        individualProduct.audioJack = "Yes"
                        appleProduct.audioJack = individualProduct.audioJack
                        ericssonProduct.audioJack = individualProduct.audioJack
                         if(!appleProduct.isSelected && !ericssonProduct.isSelected) listOfProduct.add(individualProduct)


                    }

                    cb_gps.id ->{
                        //filterMap.put("gps","Yes with A-GPS")
                        individualProduct.gps = "Yes with A-GPS"
                        appleProduct.gps = individualProduct.gps
                        ericssonProduct.gps = individualProduct.gps
                        if(!appleProduct.isSelected && !ericssonProduct.isSelected) listOfProduct.add(individualProduct)
                    }

                    cb_sim.id ->{

                    }
                }
            }

            false ->{
                when(check_box!!.id){

                    cb_apple.id ->{
                        appleProduct.isSelected = false
                        if(listOfProduct.contains(appleProduct))listOfProduct.remove(appleProduct)
                    }

                    cb_erricson.id ->{
                        ericssonProduct.isSelected = false
                        if(listOfProduct.contains(ericssonProduct))listOfProduct.remove(ericssonProduct)

                    }

                    cb_audio.id ->{

                    }

                    cb_gps.id ->{

                    }

                    cb_sim.id ->{

                    }
                }
            }
        }


    }

}