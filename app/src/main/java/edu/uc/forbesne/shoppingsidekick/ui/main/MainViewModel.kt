package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.forbesne.shoppingsidekick.dto.Product
import edu.uc.forbesne.shoppingsidekick.service.ProductService

//I used the professor's code from his github - https://github.com/discospiff/MyPlantDiaryQ
// as the base for this code

/**
 *  Gets data from server, makes adjustments, provides live data to activities
 *
 */

class MainViewModel : ViewModel() {
    var products: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productService: ProductService = ProductService()

    init {
        fetchAllProducts()
    }

    fun fetchAllProducts() {
        products = productService.fetchAllProductsFromOneStore()
    }

    fun fetchProductsByName(productName: String) {
        products = productService.fetchProductsByName(productName)
    }
}