package com.sahmed.productcatalog.framework.network.dto

data class Product(
    val announceDate: String? = "",
    var audioJack: String? = "",
    val battery: String? = "",
    val brand: String? = "",
    var gps: String? = "",
    val id: Int? = 0,
    val phone: String? = "",
    val picture: String? = "",
    val priceEur: Int? = 0,
    val resolution: String? = "",
    val sim: String? = "",
    var isSelected  :Boolean= false) {

    override fun equals(other: Any?): Boolean {
        val product = other as Product

        return (this.audioJack.equals(product.audioJack) ||
                this.gps.equals(product.gps) || this.brand.equals(product.brand) || this.sim.equals(product.sim))
    }

    override fun hashCode(): Int {
        return this.id!!
    }
}


