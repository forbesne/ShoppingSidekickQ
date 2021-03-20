package edu.uc.forbesne.shoppingsidekick.ui.main
// code is based on professor's github - https://github.com/discospiff/MyPlantDiaryQ

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.forbesne.shoppingsidekick.dto.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import edu.uc.forbesne.shoppingsidekick.dto.Cart
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import edu.uc.forbesne.shoppingsidekick.dto.Product
import edu.uc.forbesne.shoppingsidekick.dto.SearchItem
import edu.uc.forbesne.shoppingsidekick.service.ProductService


/**
 *  Gets data from Firebase and APIs, makes adjustments, provides live data to activities
 */
class MainViewModel : ViewModel() {
    private lateinit var firestore : FirebaseFirestore
    var productService: ProductService = ProductService()
    var products: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productsFromShop1: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productsFromShop2: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productsFromShop3: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var cart: Cart = Cart(ArrayList<CartItem>())

    var searchItemList: ArrayList<SearchItem> = ArrayList<SearchItem>()

    // A list of objects of type {shopName, ProductUPC, shopsPrice}
    //var initialProductPriceList :ProductPriceList = ProductPriceList(3)

    // A map/table containing all key value pairs where key = productUPC, value = array with 3 objects of type {shopName, ProductUPC,shopPrice}
    var productPricesByShopMap: HashMap<String,ProductPriceList> = HashMap<String, ProductPriceList>()

    init {
        createFirebaseInstance()
        fetchSop1Products()
        fetchSop2Products()
        fetchSop3Products()
        assignProducts()
        //populateInitialProductPriceList()
        createObservableShopsPricesMap()
    }

     fun createFirebaseInstance(){
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }


    private fun createInitialProductPriceList():ProductPriceList {
        var initialProductPriceList :ProductPriceList = ProductPriceList(3)
        initialProductPriceList.list[0].shopName = "Shop1"
        initialProductPriceList.list[1].shopName = "Shop2"
        initialProductPriceList.list[2].shopName = "Shop3"
        return initialProductPriceList
    }

    private fun fetchSop1Products() {
        productsFromShop1 = productService.fetchAllProductsFromOneStore()
    }

    private fun fetchSop2Products() {
        productsFromShop2 = productService.fetchAllProductsFromTwoStore()
    }

    private fun fetchSop3Products() {
        productsFromShop3 = productService.fetchAllProductsFromThreeStore()
    }

    private fun assignProducts() {
        //Same products in all 3 APIs, difference is prices
        products = productsFromShop1
    }

    fun fetchAllProducts():MutableLiveData<ArrayList<Product>> {
        return products
    }

    private fun createObservableShopsPricesMap() {

        productsFromShop1.observeForever {
            it.forEach {
                product->
                    var arr0 = productPricesByShopMap.get(product.UPC)
                    if(arr0 ==null){
                        //var productPriceList = initialProductPriceList
                        var productPriceList = createInitialProductPriceList()
                        productPriceList.list[0].price = product.price
                        productPricesByShopMap.put(product.UPC, productPriceList)
                    }else{
                        arr0!!.list[0].price = product.price
                        productPricesByShopMap.put(product.UPC, arr0)
                    }
            }
        }

        productsFromShop2.observeForever {
            it.forEach {
                product->
                    var arr1 = productPricesByShopMap.get(product.UPC)
                    if(arr1 ==null){
                        //var productPriceList = initialProductPriceList
                        var productPriceList = createInitialProductPriceList()
                        productPriceList.list[1].price = product.price
                        productPricesByShopMap.put(product.UPC, productPriceList)
                    }else{
                        arr1!!.list[1].price = product.price
                        productPricesByShopMap.put(product.UPC, arr1)
                    }
            }
        }

        productsFromShop3.observeForever {
            it.forEach {
                product->
                    var arr2 = productPricesByShopMap.get(product.UPC)
                    if(arr2 ==null){
                        //var productPriceList = initialProductPriceList
                        var productPriceList = createInitialProductPriceList()
                        productPriceList.list[2].price = product.price
                        productPricesByShopMap.put(product.UPC, productPriceList)
                    }else{
                        arr2!!.list[2].price = product.price
                        productPricesByShopMap.put(product.UPC, arr2)
                    }
            }
        }
    }

    fun fetchProductsByName(productName: String) {
        productsFromShop1 = productService.fetchProductsByName(productName)
    }

    fun addToCart(product: Product, quantity: Int){
        if(quantity == 0) return
        var cartItem = CartItem(product.UPC, quantity, product.imageURL, product.description)
        cart.addItem(cartItem)
    }

    fun removeFromCart(cartItem: CartItem){
        cart.removeItem(cartItem)
    }

    fun deleteCart(){
        //find function..
        cart = Cart(ArrayList<CartItem>())
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

    private fun addSearchItem(upc: String, quantity:Int) {
        searchItemList.add(SearchItem(upc, quantity))
    }

    private fun removeSearchItem(searchItem: SearchItem) {
        searchItemList.remove(searchItem)
    }

    private fun deleteSearchItemList() {
        //find function..
        searchItemList = ArrayList<SearchItem>()
    }

    // For now returns a string like: shop 1 is cheapest
    fun findCheapestMarket(): String {
        var cart1Total = 0f
        var cart2Total = 0f
        var cart3Total = 0f
        var cheapestMarket = " is the cheapest market!!"

        var itemQuantity = 0
        var itemUPC = ""
        var productPricesList = ProductPriceList(3)

        cart.itemList.forEach{
            itemQuantity = it.quantity
            itemUPC = it.UPC
            productPricesList = productPricesByShopMap.get(itemUPC)!!
            cart1Total += productPricesList.list[0].price * itemQuantity
            cart2Total += productPricesList.list[1].price * itemQuantity
            cart3Total += productPricesList.list[2].price * itemQuantity

        }
        if (cart1Total < cart2Total){
            if (cart1Total < cart3Total) {
                cheapestMarket = "Market 1$cheapestMarket"
            }
            else {
                cheapestMarket = "Market 3$cheapestMarket"
            }
        } else if (cart2Total < cart3Total) {
            cheapestMarket = "Market 2$cheapestMarket"
        }
        else {
            cheapestMarket = "Market 3$cheapestMarket"
        }

        return cheapestMarket
    }
}