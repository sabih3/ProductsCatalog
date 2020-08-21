package com.sahmed.productcatalog.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sahmed.productcatalog.R
import com.sahmed.productcatalog.framework.network.dto.Product
import com.sahmed.productcatalog.framework.utils.FilteringHelper
import kotlinx.android.synthetic.main.filter_bottom_sheet.*


class FilterScreen : BottomSheetDialogFragment(),CompoundButton.OnCheckedChangeListener{

    lateinit var listener : FilterInterface
    var queryList = mutableListOf<String>() // For inserting filtering parameters


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

        setPreselected() // Setting pre selected filter values

        /** setup all checkboxes **/

        cb_apple.setOnCheckedChangeListener(this)
        cb_erricson.setOnCheckedChangeListener(this)
        cb_audio.setOnCheckedChangeListener(this)
        cb_gps.setOnCheckedChangeListener(this)
        cb_sim.setOnCheckedChangeListener(this)
        /** --------------------------**/

        //Apply Filter Button
        apply_btn.setOnClickListener {
            listener.onFiltersApplied(queryList)
            this@FilterScreen.dismiss()
        }

        //Clear Filter Button
        clear_btn.setOnClickListener {

            cb_apple.isChecked = false
            cb_erricson.isChecked = false
            cb_audio.isChecked = false
            cb_gps.isChecked = false
            cb_sim.isChecked = false

            listener.onFiltersCleared()
        }

    }

    // This method iterates over query params list and tick mark relevant query param
    private fun setPreselected() {
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
            }
        }
    }

    //Check Boxes Check mark Change Listener
    //CompoundButton.OnCheckedChangeListener
    override fun onCheckedChanged(check_box: CompoundButton?, checked: Boolean) {
        when(checked){
            true->{
                when(check_box!!.id){

                    cb_apple.id ->{
                        queryList.add(FilteringHelper.LOOKUP_APPLE)
                    }
                    cb_erricson.id ->{
                        queryList.add(FilteringHelper.LOOKUP_ERICSSON)
                    }
                    cb_audio.id ->{
                        queryList.add(FilteringHelper.LOOKUP_AUDIOJACK)
                    }
                    cb_gps.id ->{
                        queryList.add(FilteringHelper.LOOKUP_GPS)
                    }
                    cb_sim.id ->{

                    }
                }
            }

            false ->{
                when(check_box!!.id){

                    cb_apple.id ->{
                        if(queryList.contains(FilteringHelper.LOOKUP_APPLE))queryList.remove(FilteringHelper.LOOKUP_APPLE)
                    }

                    cb_erricson.id ->{
                        if(queryList.contains(FilteringHelper.LOOKUP_ERICSSON))queryList.remove(FilteringHelper.LOOKUP_ERICSSON)

                    }

                    cb_audio.id ->{
                        if(queryList.contains(FilteringHelper.LOOKUP_AUDIOJACK))queryList.remove(FilteringHelper.LOOKUP_AUDIOJACK)
                    }

                    cb_gps.id ->{
                        if(queryList.contains(FilteringHelper.LOOKUP_GPS))queryList.remove(FilteringHelper.LOOKUP_GPS)
                    }

                    cb_sim.id ->{

                    }
                }
            }
        }
    }

    interface FilterInterface{
        fun onFiltersApplied(
            queryList: MutableList<String>?
        )
        fun onFiltersCleared()
    }
}