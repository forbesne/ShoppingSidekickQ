package edu.uc.forbesne.shoppingsidekick.ui.main
// code is based on professor's github - https://github.com/discospiff/MyPlantDiaryQ

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.forbesne.shoppingsidekick.dto.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import edu.uc.forbesne.shoppingsidekick.dto.Cart
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import edu.uc.forbesne.shoppingsidekick.dto.Product
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
    var markets: MutableLiveData<ArrayList<Market>> = MutableLiveData<ArrayList<Market>>()

    val cart: Cart = Cart()

    // A map/table containing all key value pairs where key = productUPC, value = array with 3 objects of type {shopName, ProductUPC,shopPrice}
    var productPricesByShopMap: HashMap<String,ProductPriceList> = HashMap<String, ProductPriceList>()

    init {
        createFirebaseInstance()
        fetchSop1Products()
        fetchSop2Products()
        fetchSop3Products()
        assignProducts()
        createObservableShopsPricesMap()
        getCartFromFirebase()
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

    private fun getCartFromFirebase(){
        firestore.collection("cart").addSnapshotListener {
            snapshot, e ->
            // if there is an exception we want to skip.
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen Failed", e)
                return@addSnapshotListener
            }

            // if we are here, we did not encounter an exception
            if (snapshot != null) {
                val firebaseCartItems = ArrayList<CartItem>()
                val documents = snapshot.documents
                documents.forEach {

                    val cartItem = it.toObject(CartItem::class.java)
                    if (cartItem != null) {
                        cartItem.id = it.id
                        cart.addItem(cartItem)
                    }
                }
            }
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        if (quantity <= 0) return
        var cartItem = CartItem(product.UPC, quantity, product.imageURL, product.description)

        if (cart.doesHaveItem(product.UPC)) {
            adjustCartItemQuantityInFirebase(cart.getCartItem(product.UPC), cartItem.quantity)
        }else{
            val newCartItemId = addCartItemToFirebase(cartItem)
            //adjust local cartItem to have the id of the new firebase cartItem
            cartItem.id = newCartItemId
        }
    }

    fun addCartItemToFirebase(cartItem: CartItem):String {
        val document =
            firestore.collection("cart")
                    .document()

        val cartItemId =document.id

        document.set(cartItem)
                    .addOnSuccessListener {
                        Log.d("Firebase", "document saved")
                    }
                    .addOnFailureListener{
                        Log.d("Firebase", "Save Failed")
                    }

        return cartItemId
    }

    private fun adjustCartItemQuantityInFirebase(existingCartItem: CartItem, quantityToAdd: Int) {
        var adjustedCartItem = CartItem(existingCartItem.UPC, existingCartItem.quantity)
        adjustedCartItem.id= existingCartItem.id
        adjustedCartItem.quantity += quantityToAdd

        firestore.collection("cart")
                .document(adjustedCartItem.id)
                .set(adjustedCartItem)
                .addOnSuccessListener {
                    Log.d("Firebase", "document saved")
                }
                .addOnFailureListener{
                    Log.d("Firebase", "Save Failed")
        }
    }

    fun removeFromCart(cartItem: CartItem){
        cart.removeItemFromCart(cartItem)
    }

    fun deleteCart(){
        //add remove from database..
    }

    // For now the update in the markets live data only happens when user clicks on
    // 'findCheapestMarket' - but this will change to when ever user adds item to cart.
    fun findCheapestMarket(){

        //for now the market are created statically
        var market1 = Market("Market 1",5.2f, 0f)
        var market2 = Market("Market 2",3.4f, 0f)
        var market3 = Market("Market 3",1.2f, 0f)

        var marketList = ArrayList<Market>()
        marketList.add(market1)
        marketList.add(market2)
        marketList.add(market3)

        var cart1Total = 0f
        var cart2Total = 0f
        var cart3Total = 0f
        var cheapestMarket = " is the cheapest market!!"

        var itemQuantity = 0
        var itemUPC = ""
        var productPricesList = ProductPriceList(3)

        cart.itemQuantityMap.forEach{
            itemUPC = it.key
            itemQuantity = it.value.quantity

            productPricesList = productPricesByShopMap.get(itemUPC)!!
            market1.cartPrice += productPricesList.list[0].price * itemQuantity
            market2.cartPrice += productPricesList.list[1].price * itemQuantity
            market3.cartPrice += productPricesList.list[2].price * itemQuantity
        }

        marketList.sortBy { it.cartPrice }

        markets.value = marketList
    }
}