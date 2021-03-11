package edu.uc.forbesne.shoppingsidekick.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
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
    private lateinit var firestore : FirebaseFirestore

    init {
        fetchAllProducts()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun fetchAllProducts() {
        products = productService.fetchAllProductsFromOneStore()
    }

    fun fetchProductsByName(productName: String) {
        products = productService.fetchProductsByName(productName)
    }

    fun save(cartItem: CartItem) {
        firestore.collection("cartItems")
                .document()
                .set(cartItem)
                .addOnSuccessListener {
                    Log.d("Firebase", "document saved")
                }
                .addOnFailureListener{
                    Log.d("Firebase", "Save Failed")
                }

    }


}