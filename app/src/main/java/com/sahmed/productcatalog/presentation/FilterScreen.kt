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
    override fun onCheckedChanged(check_box: CompoundButton?, checked: Boolean) {

        when(checked){
            true->{


                when(check_box!!.id){

                    cb_apple.id ->{
                        //filterMap.put("brand",cb_apple.text.toString())
                        var product = Product(brand = cb_apple.text.toString())
                        appleProduct.isSelected = true
                        listOfProduct.add(product)
                        listOfProduct.add(appleProduct)
                    }

                    cb_erricson.id ->{
                        //filterMap.put("brand",cb_erricson.text.toString())
                        var product = Product(brand = cb_erricson.text.toString())
                        ericssonProduct.isSelected = true
                        listOfProduct.add(ericssonProduct)
                        listOfProduct.add(product)
                    }

                    cb_audio.id ->{
                        //filterMap.put("audioJack","Yes")
                        var product = Product(audioJack = "Yes")
                        appleProduct.audioJack = product.audioJack
                        listOfProduct.add(product)

                    }

                    cb_gps.id ->{
                        //filterMap.put("gps","Yes with A-GPS")
                        var product = Product(gps = "Yes with A-GPS")
                        listOfProduct.add(product)
                    }

                    cb_sim.id ->{

                    }
                }
            }

            false ->{
                when(check_box!!.id){

                    cb_apple.id ->{

                    }

                    cb_erricson.id ->{
                        Log.d("","")

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