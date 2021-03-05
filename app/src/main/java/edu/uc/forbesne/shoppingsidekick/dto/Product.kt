package edu.uc.forbesne.shoppingsidekick.dto

//I used the professor's code from his github - https://github.com/discospiff/MyPlantDiaryQ
// as the base for this code

/**
 * The Product DTO.
 *
 * This DTO holds the product id, description, price, price_type, brand, category, UPC, imageUrl from api.
 * @param id The product id for api reference
 * @param description The description of the product
 * @param price The price of the product
 * @param price_type The unit to describe the product (eg. litres, pounds, etc)
 * @param brand The brand of the product
 * @param category The category of the product
 * @param UPC The UPC of the product
 * @param imageUrl The image url to fetch from api
 */
data class Product (var id: Int, var description : String, var price :Float, var price_type:String = "",
                    var brand : String, var category : String, var UPC : String, var imageURL : String) {
    override fun toString(): String {
        return "Description: $description, Price: $price, Category: $category"
    }
}