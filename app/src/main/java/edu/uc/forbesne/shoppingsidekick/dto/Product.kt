package edu.uc.forbesne.shoppingsidekick.dto
// based onp professor's code - https://github.com/discospiff/MyPlantDiaryQ

// Represents a Product in the list of products coming in from APIs
data class Product ( var UPC : String, var price :Float = 0f, var price_type:String = "",
                     var brand : String = "", var category : String="",
                      var imageURL : String="", var description : String = "",var id: Int=0) {

    override fun toString(): String {
        return "Description: $description, Price: $price, Category: $category"
    }
}
