package com.sahmed.productcatalog.framework.utils

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import com.sahmed.productcatalog.R
import kotlinx.android.synthetic.main.view_search.view.*
import kotlinx.android.synthetic.main.view_search.view.btn_close_search
import kotlinx.android.synthetic.main.view_search.view.toolbar_search_input

class SearchView :FrameLayout{
    var title :String? =  ""
    var expanded : Boolean? = false
    lateinit var searchWidgetListener: SearchWidgetListener

    constructor(context: Context) : super(context)
    constructor(context:Context,attrs: AttributeSet):super(context,attrs){
        LayoutInflater.from(context).inflate(R.layout.view_search,this,true)

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.SearchView)
        title = typedArray.getString(R.styleable.SearchView_toolbarTitle)
        val showText = typedArray.getBoolean(R.styleable.SearchView_showText, false)
        expanded = typedArray.getBoolean(R.styleable.SearchView_expanded,false)
        typedArray.recycle()

        this.findViewById<AppCompatTextView>(R.id.search_toolbar_title).text = title
        if(expanded!!){
            openSearch()
        }
        btn_open_search.setOnClickListener { openSearch() }
        btn_close_search.setOnClickListener { closeSearch() }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun openSearch() {
        icon_filter.visibility = View.GONE
        open_search_view.visibility = View.VISIBLE
        btn_open_search.visibility = View.GONE
        toolbar_search_input.requestFocus()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            open_search_view,
            ((btn_open_search.right + btn_open_search.left) / 2),
            ((btn_open_search.top + btn_open_search.bottom) / 2),
            0f, width.toFloat()
        )
        circularReveal.duration = 400
        circularReveal.start()
    }

    private fun closeSearch() {
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            open_search_view,
            ((btn_open_search.right + btn_open_search.left) / 2),
            ((btn_open_search.top + btn_open_search.bottom) / 2),
            width.toFloat(), 0f
        )

        circularConceal.duration = 400
        circularConceal.start()

        circularConceal.addListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) = Unit

            override fun onAnimationEnd(animation: Animator?){
                toolbar_search_input.clearFocus()
                icon_filter.visibility = View.VISIBLE
                btn_open_search.visibility = View.VISIBLE
                open_search_view.visibility = View.INVISIBLE
                //toolbar_search_input.setText("")
                circularConceal.removeAllListeners()

                if(::searchWidgetListener.isInitialized)searchWidgetListener.onCloseSearch()
            }

            override fun onAnimationCancel(animation: Animator?) = Unit

            override fun onAnimationStart(animation: Animator?) = Unit

        })
    }

    fun setListener(searchWidgetListener: SearchWidgetListener){

    }
    interface SearchWidgetListener{
        fun onCloseSearch()
    }
}