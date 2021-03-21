package edu.uc.forbesne.shoppingsidekick.dto
// code is based on professor's github - https://github.com/discospiff/MyPlantDiaryQ


data class CartItem (var UPC : String ="", var quantity: Int = 0, var imageURL : String ="", var description : String ="",
                      var productBrand: String = "", var measurementUnit: String = "",var id:String = "" ) {

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