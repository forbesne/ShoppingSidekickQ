package edu.uc.forbesne.shoppingsidekick.dto

data class CartItem (var productName: String = "", var productBrand: String = "", var productId: String = "",
                     var measurementUnit: String = "", var quantity: Int=1, var cartId:Int = 0){
}