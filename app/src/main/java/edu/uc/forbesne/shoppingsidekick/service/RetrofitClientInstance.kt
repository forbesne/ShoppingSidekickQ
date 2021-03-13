package edu.uc.forbesne.shoppingsidekick.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//I used the professor's code from his github - https://github.com/discospiff/MyPlantDiaryQ
// as the base for this code
/**
 * Manages http requests
 *
 */
object RetrofitClientInstance {

    private var retrofit: Retrofit? = null;
    private val BASE_URL = "http://benjamingomori.com/shopping_side_kick/api/"

    val retrofitInstance : Retrofit?
        get() {
            // has this object been created yet?
            if (retrofit == null) {
                // create it!
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}