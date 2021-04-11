package edu.uc.forbesne.shoppingsidekick.dto
// code is based on professor's github - https://github.com/discospiff/MyPlantDiaryQ

/**
 * Data class that represents a Cart Item
 *
 * @param UPC The UPC of the product
 * @param quantity number of particular cart items in the cart
 * @param imageURL The image url to fetch from api
 * @param description The description of the product
 * @param productBrand The brand of the product
 * @param measurementUnit unit of measurement for the product (eg: litres, kgs, etc)
 * @param id The product id for api reference
 */
data class CartItem (var UPC : String ="", var quantity: Int = 0, var imageURL : String ="", var description : String ="",
                      var productBrand: String = "", var measurementUnit: String = "",var id:String = "" ) {

    override fun toString(): String {
        return "$description $productBrand $quantity $measurementUnit"
    }

    fun add(quantity: Int){
        this.quantity += quantity
    }

    fun reduce(quantity: Int){
        this.quantity -= quantity
        if(this.quantity < 0) this.quantity = 0
    }

    fun remove(){
        this.quantity = 0
    }
}