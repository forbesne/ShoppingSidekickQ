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

    // A list of object{shopName, ProductUPC, shopsPrice}
    var initialProductPriceList :ProductPriceList = ProductPriceList(3)
    // A map/table containing all key value pairs where key = productUPC, value = list of objects {shopName, ProductUPC,shopPrice}
    var productPricesByShopMap: HashMap<String,ProductPriceList> = HashMap<String, ProductPriceList>()

    init {
        createFirebaseInstance()
        fetchSop1Products()
        fetchSop2Products()
        fetchSop3Products()
        assignProducts()
        populateInitialProductPriceList()
        createObservableShopsPricesMap()
    }

     fun createFirebaseInstance(){
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
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

    private fun populateInitialProductPriceList() {
        initialProductPriceList.list[0].shopName = "Shop1"
        initialProductPriceList.list[1].shopName = "Shop2"
        initialProductPriceList.list[2].shopName = "Shop3"
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
                product-> {
                    var arr0 = productPricesByShopMap.get(product.UPC)
                    if(arr0 ==null){
                        var productPriceList = initialProductPriceList
                        productPriceList.list[0].price = product.price
                        productPricesByShopMap.put(product.UPC, productPriceList)
                    }else{
                        arr0!!.list[0].price = product.price
                        productPricesByShopMap.put(product.UPC, arr0)
                    }
                }
            }
        }

        productsFromShop2.observeForever {
            it.forEach {
                product-> {
                    var arr1 = productPricesByShopMap.get(product.UPC)
                    if(arr1 ==null){
                        var productPriceList = initialProductPriceList
                        productPriceList.list[1].price = product.price
                        productPricesByShopMap.put(product.UPC, productPriceList)
                    }else{
                        arr1!!.list[1].price = product.price
                        productPricesByShopMap.put(product.UPC, arr1)
                    }
                }

            }
        }

        productsFromShop3.observeForever {
            it.forEach {
                product-> {
                    var arr2 = productPricesByShopMap.get(product.UPC)
                    if(arr2 ==null){
                        var productPriceList = initialProductPriceList
                        productPriceList.list[2].price = product.price
                        productPricesByShopMap.put(product.UPC, productPriceList)
                    }else{
                        arr2!!.list[1].price = product.price
                        productPricesByShopMap.put(product.UPC, arr2)
                    }
                }
            }
        }
    }

    fun fetchProductsByName(productName: String) {
        productsFromShop1 = productService.fetchProductsByName(productName)
    }

    // For now returns a string like: shop 1 is cheapest
    fun findCheapestMarket(): String {
        var cart1Total = 0f
        var cart2Total = 0f
        var cart3Total = 0f
        var cheapestMarket = " is the cheapest market!!"

        var itemAmount = 0
        var itemUPC = ""
        var productPricesList = ProductPriceList(3)

        cart.itemList.forEach{
            itemAmount = it.quantity
            itemUPC = it.UPC
            productPricesList = productPricesByShopMap.get(itemUPC)!!
            cart1Total += productPricesList.list[0].price * itemAmount
            cart2Total += productPricesList.list[1].price * itemAmount
            cart3Total += productPricesList.list[2].price * itemAmount

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
}