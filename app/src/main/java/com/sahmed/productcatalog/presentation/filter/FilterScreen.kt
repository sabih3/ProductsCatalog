package com.sahmed.productcatalog.presentation.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rizlee.rangeseekbar.RangeSeekBar
import com.sahmed.productcatalog.R
import com.sahmed.productcatalog.framework.utils.FilteringHelper
import kotlinx.android.synthetic.main.component_filter_buttons.*
import kotlinx.android.synthetic.main.filter_bottom_sheet.*


class FilterScreen : BottomSheetDialogFragment(),CompoundButton.OnCheckedChangeListener{

    companion object{
        val DEFAULT_MIN_VALUE = 50
        val DEFAULT_MAX_VALUE = 15000
    }

    lateinit var listener : FilterInterface
    var queryList = mutableListOf<String>() // For inserting filtering parameters
    var priceMinSelected =
        DEFAULT_MIN_VALUE
    var priceMaxSelected =
        DEFAULT_MAX_VALUE

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
        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        // Setting Price Seekbar prior to pre selected, because it's range listener was disturbing the pre selected values after
        setupPriceSeekbar()
        setPreselected() // Setting pre selected filter values

        /** setup all checkboxes **/
        cb_apple.setOnCheckedChangeListener(this)
        cb_erricson.setOnCheckedChangeListener(this)
        cb_audio.setOnCheckedChangeListener(this)
        cb_gps.setOnCheckedChangeListener(this)
        cb_no_sim.setOnCheckedChangeListener(this)
        cb_single_sim.setOnCheckedChangeListener(this)
        cb_mini_sim.setOnCheckedChangeListener(this)
        cb_micro_sim.setOnCheckedChangeListener(this)
        cb_nano_esim.setOnCheckedChangeListener(this)
        cb_esim.setOnCheckedChangeListener(this)
        /** --------------------------**/

        //Apply Filter Button
        apply_btn.setOnClickListener {
            if(queryList.size>0 ||
                priceMinSelected!= DEFAULT_MIN_VALUE ||
                priceMaxSelected!= priceMaxSelected)listener.onFiltersApplied(queryList,priceMinSelected,priceMaxSelected)

            this@FilterScreen.dismiss()
        }

        //Clear Filter Button
        clear_btn.setOnClickListener {

            cb_apple.isChecked = false
            cb_erricson.isChecked = false
            cb_audio.isChecked = false
            cb_gps.isChecked = false
            cb_no_sim.isChecked = false
            cb_single_sim.isChecked = false
            cb_mini_sim.isChecked = false
            cb_micro_sim.isChecked = false
            cb_nano_esim.isChecked = false
            cb_esim.isChecked = false

            priceMinSelected =
                DEFAULT_MIN_VALUE
            priceMaxSelected =
                DEFAULT_MAX_VALUE
            rangeSeekBar.setCurrentValues(priceMinSelected,priceMaxSelected)
            selectedPriceList.clear()
            listener.onFiltersCleared()
        }



    }

    // This method iterates over query params list and tick mark relevant query param
    private fun setPreselected() {
        showFilterCount(queryList.size)
        queryList.forEach {
            when(it){
                FilteringHelper.LOOKUP_APPLE ->{
                    cb_apple.isChecked = true
                }

                FilteringHelper.LOOKUP_ERICSSON ->{
                    cb_erricson.isChecked = true
                }

                FilteringHelper.LOOKUP_GPS ->{
                    cb_gps.isChecked = true
                }

                FilteringHelper.LOOKUP_AUDIOJACK ->{
                    cb_audio.isChecked = true
                }

                FilteringHelper.LOOKUP_NO_SIM ->{
                    cb_no_sim.isChecked = true
                }

                FilteringHelper.LOOKUP_SINGLE_SIM ->{
                    cb_single_sim.isChecked = true
                }

                FilteringHelper.LOOKUP_MINI_SIM ->{
                    cb_mini_sim.isChecked = true
                }

                FilteringHelper.LOOKUP_MICRO_SIM->{
                    cb_micro_sim.isChecked = true
                }

                FilteringHelper.LOOKUP_NANO_SIM->{
                    cb_nano_esim.isChecked = true
                }

                FilteringHelper.LOOKUP_E_SIM -> {
                    cb_esim.isChecked = true
                }
            }
        }
    }

    //Check Boxes Check mark Change Listener
    //CompoundButton.OnCheckedChangeListener
    override fun onCheckedChanged(check_box: CompoundButton?, checked: Boolean) {
        when (checked) {
            true -> {
                when (check_box!!.id) {

                    cb_apple.id -> {
                        queryList.add(FilteringHelper.LOOKUP_APPLE)
                    }
                    cb_erricson.id -> {
                        queryList.add(FilteringHelper.LOOKUP_ERICSSON)
                    }
                    cb_audio.id -> {
                        queryList.add(FilteringHelper.LOOKUP_AUDIOJACK)
                    }
                    cb_gps.id -> {
                        queryList.add(FilteringHelper.LOOKUP_GPS)
                    }

                    cb_no_sim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_NO_SIM,false)
                    }

                    cb_single_sim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_SINGLE_SIM,false)
                    }

                    cb_mini_sim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_MINI_SIM,false)
                    }

                    cb_micro_sim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_MICRO_SIM,false)
                    }

                    cb_nano_esim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_NANO_SIM,false)
                    }

                    cb_esim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_E_SIM,false)
                    }

                }
            }

            false -> {
                when (check_box!!.id) {

                    cb_apple.id -> {
                        if (queryList.contains(FilteringHelper.LOOKUP_APPLE)) queryList.remove(
                            FilteringHelper.LOOKUP_APPLE
                        )
                    }

                    cb_erricson.id -> {
                        if (queryList.contains(FilteringHelper.LOOKUP_ERICSSON)) queryList.remove(
                            FilteringHelper.LOOKUP_ERICSSON
                        )

                    }

                    cb_audio.id -> {
                        if (queryList.contains(FilteringHelper.LOOKUP_AUDIOJACK)) queryList.remove(
                            FilteringHelper.LOOKUP_AUDIOJACK
                        )
                    }

                    cb_gps.id -> {
                        if (queryList.contains(FilteringHelper.LOOKUP_GPS)) queryList.remove(
                            FilteringHelper.LOOKUP_GPS
                        )
                    }

                    cb_no_sim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_NO_SIM,true)
                    }

                    cb_single_sim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_SINGLE_SIM,true)
                    }

                    cb_mini_sim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_MINI_SIM,true)
                    }

                    cb_micro_sim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_MICRO_SIM,true)
                    }

                    cb_nano_esim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_NANO_SIM,true)
                    }

                    cb_esim.id -> {
                        updateFilterParam(FilteringHelper.LOOKUP_E_SIM,true)
                    }
                }
            }
        }

        showFilterCount(queryList.size)

    }

    private fun setupPriceSeekbar() {
        rangeSeekBar.setCurrentValues(priceMinSelected,priceMaxSelected)
        rangeSeekBar.listenerPost = object: RangeSeekBar.OnRangeSeekBarPostListener {
            override fun onValuesChanged(minValue: Float, maxValue: Float) {
                TODO("Not yet implemented")
            }

            override fun onValuesChanged(minValue: Int, maxValue: Int) {
                priceMinSelected = minValue
                priceMaxSelected = maxValue
                txt_min.text = minValue.toString()
                txt_max.text = maxValue.toString()
            }

        }

    }

    fun showFilterCount(count:Int) = selection_count.setText(resources.getQuantityText(R.plurals.filterCount,count))

    fun updateFilterParam(param:String,toRemove:Boolean){
        if(toRemove){
            if(queryList.contains(param)){
                queryList.remove(param)
            }
        }else{
            if(queryList.size ==2 &&  queryList.contains(FilteringHelper.LOOKUP_PRICE_MIN) &&
                queryList.contains(FilteringHelper.LOOKUP_PRICE_MAX)){
                queryList.clear()
                queryList.add(param)
            }else{
                queryList.add(param)
            }

        }
    }

    var selectedPriceList = mutableListOf<String>()

    fun updateParamForPrice(priceKey:String,value:String, toRemove:Boolean = false){
        //val key_n_value = priceKey + " " + value
        if(selectedPriceList.size>=2 && selectedPriceList.contains(priceKey)){
            selectedPriceList.remove(priceKey)
            selectedPriceList.add(priceKey)
            selectedPriceList.forEach {
                //queryList.add(priceKey+ " "+ value)
            }
        }else{
            selectedPriceList.add(priceKey)
//            selectedPriceList.forEach {
//                queryList.add(priceKey+ " "+ value)
//            }
        }

    }


    interface FilterInterface{
        fun onFiltersApplied(
            queryList: MutableList<String>?,
            priceRangeMinValue: Int,
            priceRangeMaxValue: Int
        )
        fun onFiltersCleared()
    }
}