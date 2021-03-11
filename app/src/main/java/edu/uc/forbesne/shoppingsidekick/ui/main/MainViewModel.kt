package edu.uc.forbesne.shoppingsidekick.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import edu.uc.forbesne.shoppingsidekick.dto.Cart
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import edu.uc.forbesne.shoppingsidekick.dto.Product
import edu.uc.forbesne.shoppingsidekick.dto.SearchItem
import edu.uc.forbesne.shoppingsidekick.service.ProductService

//I used the professor's code from his github - https://github.com/discospiff/MyPlantDiaryQ
// as the base for this code

/**
 *  Gets data from server, makes adjustments, provides live data to activities
 *
 */
class MainViewModel : ViewModel() {
    var productService: ProductService = ProductService()
    var productsFromShop1: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productsFromShop2: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productsFromShop3: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var cart: Cart = Cart(ArrayList<CartItem>())
    var searchItemList: ArrayList<SearchItem> = ArrayList<SearchItem>()
    private lateinit var firestore : FirebaseFirestore

    init {
        fetchSop1Products()
        fetchSop2Products()
        fetchSop3Products()
        fetchAllProducts()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    private fun fetchSop1Products() {
        productsFromShop1 = productService.fetchAllProductsFromOneStore()    }

    private fun fetchSop2Products() {
        productsFromShop2 = productService.fetchAllProductsFromTwoStore()
    }

    private fun fetchSop3Products() {
        productsFromShop3 = productService.fetchAllProductsFromThreeStore()
    }

    fun fetchAllProducts():MutableLiveData<ArrayList<Product>> {
        //We have same products in all 3 APIs, difference is prices
        return productsFromShop1
    }

    fun fetchProductsByName(productName: String) {
        productsFromShop1 = productService.fetchProductsByName(productName)
    }


    fun findCheapestMarket() {

    }

    fun addToCart(product: Product, amount: Int){
        if(amount == 0) return
        var cartItem = CartItem(product.description, product.UPC, amount, product.imageURL)
        cart.addItem(cartItem)
    }

    fun removeFromCart(cartItem: CartItem){
        cart.removeItem(cartItem)
    }

    fun deleteCart(){
        //find function..
        cart = Cart(ArrayList<CartItem>())
    }

    private fun addSearchItem(upc: String, amount:Int) {
        searchItemList.add(SearchItem(upc, amount))
    }

    private fun removeSearchItem(searchItem: SearchItem) {
        searchItemList.remove(searchItem)
    }

    private fun deleteSearchItemList() {
        //find function..
        searchItemList = ArrayList<SearchItem>()
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