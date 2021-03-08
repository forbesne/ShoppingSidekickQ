package edu.uc.forbesne.shoppingsidekick.dto

//I used the professor's code from his github - https://github.com/discospiff/MyPlantDiaryQ
// as the base for this code

/**
 * DTO contains parameters:
 * id
 * description
 * price
 * price_type
 * brand
 * category
 * UPC
 * imageURL
 */

data class Product (var id: Int, var description : String, var price :Float, var price_type:String = "",
                    var brand : String, var category : String, var UPC : String, var imageURL : String) {
    override fun toString(): String {
        return "Description: $description, Price: $price, Category: $category"
    }
}