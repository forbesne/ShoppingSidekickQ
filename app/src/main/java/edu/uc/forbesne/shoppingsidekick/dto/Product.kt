package edu.uc.forbesne.shoppingsidekick.dto

// code is based on professor's github - https://github.com/discospiff/MyPlantDiaryQ

data class Product (var id: Int, var description : String, var price :Float, var price_type:String = "",
                    var brand : String, var category : String, var UPC : String, var imageURL : String) {
    override fun toString(): String {
        return "Description: $description, Price: $price, Category: $category"
    }
}