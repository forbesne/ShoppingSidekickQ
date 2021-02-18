package edu.uc.forbesne.shoppingsidekick.dao

import edu.uc.forbesne.shoppingsidekick.dto.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//I used the professor's code from his github - https://github.com/discospiff/MyPlantDiaryQ
// as the base for this code

/**
 * Used by Retrofit instance to get make http requests
 *
 */
interface IProductDAO {
    @GET("shop_1.php")
    fun getAllProductsFromOneStore(): Call<ArrayList<Product>>

/*    @GET()
    fun getProductsFromOneStore(@Query(")) : Call<ArrayList<Product>>*/
}