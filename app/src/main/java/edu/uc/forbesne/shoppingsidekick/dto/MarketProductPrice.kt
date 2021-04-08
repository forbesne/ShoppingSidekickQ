package edu.uc.forbesne.shoppingsidekick.dto

/**
 * Data Class that represents market product price
 *
 * @param shopName Name of the shop
 * @param upc UPC of the product
 * @param price Price of the product
 */
data class MarketProductPrice (var shopName : String ="", var upc :String = "", var price: Float =0.0f) {
}