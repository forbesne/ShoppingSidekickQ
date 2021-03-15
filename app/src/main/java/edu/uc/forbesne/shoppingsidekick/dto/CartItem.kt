package edu.uc.forbesne.shoppingsidekick.dto


data class CartItem (var cartId:Int = 0,  var UPC : String, var quantity: Int = 0, var imageURL : String, var description : String,
                      var productBrand: String = "", var measurementUnit: String = "") {

    fun add(quantity: Int){
        this.quantity += quantity
    }

    fun reduce(quantity: Int){
        this.quantity -= quantity
        if(this.quantity < 0) this.quantity = 0
    }

    fun remove(quantity: Int){
        this.quantity = 0
    }
}