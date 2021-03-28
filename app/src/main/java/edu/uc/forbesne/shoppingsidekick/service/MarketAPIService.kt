package edu.uc.forbesne.shoppingsidekick.service
// based onp professor's code - https://github.com/discospiff/MyPlantDiaryQ

import androidx.lifecycle.MutableLiveData
import edu.uc.forbesne.shoppingsidekick.dao.IProductDAO
import edu.uc.forbesne.shoppingsidekick.dto.MarketApiObject
import edu.uc.forbesne.shoppingsidekick.dto.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

/**
 * Makes calls to APIs to get markets data
 *
 */
class MarketAPIService {

    fun fetchProductsByName(productName: String) : MutableLiveData<ArrayList<Product>> {
        return MutableLiveData<ArrayList<Product>>()
    }

    fun fetchAllDataFromMarket1() : MutableLiveData<MarketApiObject> {
        var _marketApiObject: MutableLiveData<MarketApiObject> = MutableLiveData<MarketApiObject>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IProductDAO::class.java)
        val call = service?.getMarketApi1()
        call?.enqueue(object : Callback<MarketApiObject> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<MarketApiObject>, t: Throwable) {
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: Call<MarketApiObject>,
                response: Response<MarketApiObject>
            ) {
                _marketApiObject.value = response.body()
            }

        })

        return _marketApiObject
    }

    fun fetchAllDataFromMarket2() : MutableLiveData<MarketApiObject> {
        var _marketApiObject = MutableLiveData<MarketApiObject>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IProductDAO::class.java)
        val call = service?.getMarketApi2()
        call?.enqueue(object : Callback<MarketApiObject> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<MarketApiObject>, t: Throwable) {
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                    call: Call<MarketApiObject>,
                    response: Response<MarketApiObject>
            ) {
                _marketApiObject.value = response.body()
         }

        })

        return _marketApiObject
    }

    fun fetchAllDataFromMarket3() : MutableLiveData<MarketApiObject> {
        var _marketApiObject = MutableLiveData<MarketApiObject>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IProductDAO::class.java)
        val call = service?.getMarketApi3()
        call?.enqueue(object : Callback<MarketApiObject> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<MarketApiObject>, t: Throwable) {
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                    call: Call<MarketApiObject>,
                    response: Response<MarketApiObject>
            ) {
                _marketApiObject.value = response.body()
            }

        })

        return _marketApiObject
    }
}