package edu.uc.forbesne.shoppingsidekick.dto

data class Shops1ProductPriceList (var size :Int ) {
    val list:Array<ShopProductPrice> = Array<ShopProductPrice>(size){ShopProductPrice()}
}