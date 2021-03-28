package edu.uc.forbesne.shoppingsidekick.dto

// Used to represent one product's array of markets prices.
// Size is hard coded to 3 in mainViewModel (for 3 APIs)
data class ProductPriceList (var size :Int ) {
    val list:Array<MarketProductPrice> = Array<MarketProductPrice>(size){MarketProductPrice()}
}