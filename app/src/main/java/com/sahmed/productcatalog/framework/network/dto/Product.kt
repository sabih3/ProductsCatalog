package com.sahmed.productcatalog.framework.network.dto

data class Product(
    val announceDate: String? = "",
    val audioJack: String? = "",
    val battery: String? = "",
    val brand: String? = "",
    val gps: String? = "",
    val id: Int? = 0,
    val phone: String? = "",
    val picture: String? = "",
    val priceEur: Int? = 0,
    val resolution: String? = "",
    val sim: String? = "",
    var isSelected  :Boolean= false




)

    {
        override fun equals(other: Any?): Boolean {
            val product = other as Product
            return this.audioJack.equals(other?.audioJack,true) ||
                    this.gps.equals(other?.gps,true) || this.brand.equals(other?.brand) || this.sim.equals(other?.sim)
        }

}