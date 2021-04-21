package edu.uc.forbesne.shoppingsidekick.dto
// based onp professor's code - https://github.com/discospiff/MyPlantDiaryQ

/**
 * Data class that holds the product id, description, price, price_type, brand, category, UPC, imageUrl from api.
 *
 * @param UPC The UPC of the product
 * @param price The price of the product
 * @param price_type The unit to describe the product (eg. litres, pounds, etc)
 * @param brand The brand of the product
 * @param category The category of the product
 * @param imageUrl The image url to fetch from api
 * @param description The description of the product
 * @param id The product id
 */
data class Product ( var UPC : String, var price :Float = 0f, var price_type:String = "",
                     var brand : String = "", var category : String="",
                      var imageURL : String="", var description : String = "",var id: Int=0) {

    override fun toString(): String {
        return "Description: $description, Price: $price, Category: $category"
    }
}
