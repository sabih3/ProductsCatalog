package com.sahmed.productcatalog.presentation.product_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sahmed.productcatalog.R
import com.sahmed.productcatalog.framework.network.dto.Product
import kotlinx.android.synthetic.main.grid_item.view.*

class ProductsAdapter : RecyclerView.Adapter<ProductHolder>(){

    var data = listOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.grid_item,parent,false)

        var holder =
            ProductHolder(row)

        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = data.get(position)
        holder.bindData(product)
    }

}


class ProductHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    fun bindData(product: Product) {
        itemView.row_brand.text = product.phone
        itemView.row_price.text = "€ " + product.priceEur.toString()

        Glide.with(itemView.context)
            .load(product.picture)
            //                        .override(350,450)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(0.8f)
            .into(itemView.row_image)

        itemView.row_image.setScaleType(ImageView.ScaleType.CENTER_CROP)
    }

}