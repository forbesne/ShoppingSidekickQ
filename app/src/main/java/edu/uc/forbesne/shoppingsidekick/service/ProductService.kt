package edu.uc.forbesne.shoppingsidekick.service


import androidx.lifecycle.MutableLiveData
import edu.uc.forbesne.shoppingsidekick.dao.IProductDAO
import edu.uc.forbesne.shoppingsidekick.dto.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//I used the professor's code from his github - https://github.com/discospiff/MyPlantDiaryQ
// as the base for this code

/**
 * Makes calls to APIs to get product data
 *
 */

class ProductService {

    fun fetchAllProductsFromOneStore() : MutableLiveData<ArrayList<Product>> {
        var _products = MutableLiveData<ArrayList<Product>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IProductDAO::class.java)
        val call = service?.getAllProductsFromOneStore()
        call?.enqueue(object : Callback<ArrayList<Product>> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: Call<ArrayList<Product>>,
                response: Response<ArrayList<Product>>
            ) {
                _products.value = response.body()
            }

        })

        return _products
    }
}