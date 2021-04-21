package edu.uc.forbesne.shoppingsidekick.dao
// based on professor's code - https://github.com/discospiff/MyPlantDiaryQ

import edu.uc.forbesne.shoppingsidekick.dto.MarketApiObject
import edu.uc.forbesne.shoppingsidekick.dto.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used by Retrofit instance to get make http requests
 *
 */
interface IMarketApiObjectDAO {

    @GET("market_1.php")
    fun getMarketApi1(): Call<MarketApiObject>

    @GET("market_2.php")
    fun getMarketApi2(): Call<MarketApiObject>

    @GET("market_3.php")
    fun getMarketApi3(): Call<MarketApiObject>

}