package edu.uc.forbesne.shoppingsidekick.dto

/**
 * Data class to store Product Price List from different markets.
 *
 * @param size number of markets
 * @param list a list of MarketProductPrice
 */
data class ProductPriceList (var size :Int ) {
    val list:Array<MarketProductPrice> = Array<MarketProductPrice>(size){MarketProductPrice()}
}