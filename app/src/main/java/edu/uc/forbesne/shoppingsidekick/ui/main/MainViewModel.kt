package edu.uc.forbesne.shoppingsidekick.ui.main
// code is based on professor's github - https://github.com/discospiff/MyPlantDiaryQ

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import edu.uc.forbesne.shoppingsidekick.dto.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import edu.uc.forbesne.shoppingsidekick.dto.Cart
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import edu.uc.forbesne.shoppingsidekick.dto.Product
import edu.uc.forbesne.shoppingsidekick.service.MarketAPIService

/**
 *  Gets data from Firebase and APIs, builds objects to provide live data
 */
class MainViewModel : ViewModel() {

    private lateinit var firestore: FirebaseFirestore
    var marketAPIService: MarketAPIService = MarketAPIService()

    // Used for tests
    var productsFromShop1: MutableLiveData<ArrayList<Product>> =
        MutableLiveData<ArrayList<Product>>()

    // Holds actual data fetched from APIs wrapped as live data.
    // Incoming data will be exactly like this{market: {name:'', distance:''}, products: [...] }
    var marketApiObject1: MutableLiveData<MarketApiObject> = MutableLiveData<MarketApiObject>()
    var marketApiObject2: MutableLiveData<MarketApiObject> = MutableLiveData<MarketApiObject>()
    var marketApiObject3: MutableLiveData<MarketApiObject> = MutableLiveData<MarketApiObject>()

    // Used to hold a list of market part of the incoming data from APis {market: {name:'', distance: '''}, ...}
    private var _markets: MutableLiveData<ArrayList<Market>> = MutableLiveData<ArrayList<Market>>()
    var markets: MutableLiveData<ArrayList<Market>>? = null
        get() {
            updateMarketsTotals()
            return _markets
        }

    // Used to hold the products part of the incoming data{... , products: [] }
    // For simplicity reasons, we have all the APIs return the same products, and only differ in prices
    // Thus, we assign 'products' (later) one list of products from one market API call
    var products: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()

    // Holds cart data, its state is 'managed' by firebase using addSnapshotListener
    private val cart: Cart = Cart()

    // A HashMap containing all products and all their market prices as key value pairs. Where
    // key = productUPC, value = array with 3 objects of type {shopName, ...,shopPrice}
    var productPricesByShopMap: HashMap<String, ProductPriceList> =
        HashMap<String, ProductPriceList>()

    init {
        createFirebaseInstance()
        fetchMarket1()
        fetchMarket2()
        fetchMarket3()
        assignProducts()
        createObservablesFromApisData()
        getCartFromFirebase()
    }

    private fun createFirebaseInstance() {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    private fun createInitialProductPriceList(): ProductPriceList {
        var initialProductPriceList: ProductPriceList = ProductPriceList(3)
        initialProductPriceList.list[0].shopName = "Shop1"
        initialProductPriceList.list[1].shopName = "Shop2"
        initialProductPriceList.list[2].shopName = "Shop3"
        return initialProductPriceList
    }

    private fun fetchMarket1() {
        marketApiObject1 = marketAPIService.fetchAllDataFromMarket1()
    }

    private fun fetchMarket2() {
        marketApiObject2 = marketAPIService.fetchAllDataFromMarket2()
    }

    private fun fetchMarket3() {
        marketApiObject3 = marketAPIService.fetchAllDataFromMarket3()
    }

    private fun assignProducts() {
        //Same products in all 3 APIs, difference is prices
        marketApiObject1.observeForever {
            products.value = it.products
        }
    }

    // provides products data (to main fragment)
    fun fetchAllProducts(): MutableLiveData<ArrayList<Product>> {
        return products
    }

    // Separates the incoming api data into Market data and Products data.
    // Creates from them observables
    private fun createObservablesFromApisData() {
        createObservableMarkets()
        createObservableShopsPricesMap()
    }

    private fun createObservableMarkets() {

        var market1 = Market()
        var market2 = Market()
        var market3 = Market()

        _markets.value = ArrayList<Market>()
        _markets.value!!.add(market1)
        _markets.value!!.add(market2)
        _markets.value!!.add(market3)

        // Market data could theoretically change, so we observe the api objects for that
        marketApiObject1.observeForever {
            _markets.value!![0].name = it.market.name
            _markets.value!![0].distance = it.market.distance
        }

        marketApiObject2.observeForever {
            _markets.value!![1].name = it.market.name
            _markets.value!![1].distance = it.market.distance
        }

        marketApiObject3.observeForever {
            _markets.value!![2].name = it.market.name
            _markets.value!![2].distance = it.market.distance
        }
    }

    // Observes api market data to update for any changes in the products.
    // First time product entry (with shop prices) is added to map,
    // other times price (for a shop) is updated for map's product
    private fun createObservableShopsPricesMap() {

        marketApiObject1.observeForever {
            it.products.forEach { product ->
                var arr0 = productPricesByShopMap.get(product.UPC)
                if (arr0 == null) {
                    var productPriceList = createInitialProductPriceList()
                    productPriceList.list[0].price = product.price
                    productPricesByShopMap.put(product.UPC, productPriceList)
                } else {
                    arr0!!.list[0].price = product.price
                    productPricesByShopMap.put(product.UPC, arr0)
                }
            }
        }

        marketApiObject2.observeForever {
            it.products.forEach { product ->
                var arr1 = productPricesByShopMap.get(product.UPC)
                if (arr1 == null) {
                    var productPriceList = createInitialProductPriceList()
                    productPriceList.list[1].price = product.price
                    productPricesByShopMap.put(product.UPC, productPriceList)
                } else {
                    arr1!!.list[1].price = product.price
                    productPricesByShopMap.put(product.UPC, arr1)
                }
            }
        }

        marketApiObject3.observeForever {
            it.products.forEach { product ->
                var arr2 = productPricesByShopMap.get(product.UPC)
                if (arr2 == null) {
                    var productPriceList = createInitialProductPriceList()
                    productPriceList.list[2].price = product.price
                    productPricesByShopMap.put(product.UPC, productPriceList)
                } else {
                    arr2!!.list[2].price = product.price
                    productPricesByShopMap.put(product.UPC, arr2)
                }
            }
        }
    }

    // For now only used for testing, later will make use of text search using the auto complete view
    fun fetchProductsByName(productName: String) {
        productsFromShop1 = marketAPIService.fetchProductsByName(productName)
    }

    private fun getCartFromFirebase() {
        firestore.collection("cart").addSnapshotListener { snapshot, e ->
            // if there is an exception we want to skip.
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen Failed", e)
                return@addSnapshotListener
            }

            // if we are here, we did not encounter an exception
            if (snapshot != null) {
                val documents = snapshot.documents
                documents.forEach {

                    val cartItem = it.toObject(CartItem::class.java)
                    if (cartItem != null) {
                        // Sets id to the one firebase creates. used to update item's quantity in firebase
                        cartItem.id = it.id
                        cart.addItem(cartItem)
                    }
                }
            }
        }
    }

    // Gets called from product pop-up view to add product to cart
    fun addToCart(product: Product, quantity: Int) {
        if (quantity <= 0) return
        var cartItem = CartItem(product.UPC, quantity, product.imageURL, product.description)

        if (cart.doesHaveItem(product.UPC)) {
            adjustCartItemQuantityInFirebase(cart.getCartItem(product.UPC), quantity)
        } else {
            addCartItemToFirebase(cartItem)
        }
    }

    private fun addCartItemToFirebase(cartItem: CartItem): String {
        val document =
            firestore.collection("cart")
                .document()

        val cartItemId = document.id

        document.set(cartItem)
            .addOnSuccessListener {
                Log.d("Firebase", "document saved")
            }
            .addOnFailureListener {
                Log.d("Firebase", "Save Failed")
            }

        return cartItemId
    }

    private fun adjustCartItemQuantityInFirebase(existingCartItem: CartItem, quantityToAdd: Int) {
        var adjustedCartItem = CartItem(existingCartItem.UPC, existingCartItem.quantity)
        adjustedCartItem.id = existingCartItem.id
        adjustedCartItem.quantity += quantityToAdd

        firestore.collection("cart")
            .document(adjustedCartItem.id)
            .set(adjustedCartItem)
            .addOnSuccessListener {
                Log.d("Firebase", "document saved")
            }
            .addOnFailureListener {
                Log.d("Firebase", "Save Failed")
            }
    }

    // not implemented yet
    fun removeFromCart(cartItem: CartItem) {
        cart.removeItemFromCart(cartItem)

    }
    // not implemented yet
    fun deleteCart() {
        cart.emptyCart();
        firestore.collection("cart").get().addOnSuccessListener { querySnapshot ->
            for (documentSnapshot in querySnapshot) {
                firestore.collection("cart").document(documentSnapshot.id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d(
                            "Firebase",
                            "DocumentSnapshot successfully deleted!"
                        )
                    }
                    .addOnFailureListener { e -> Log.w("Firebase", "Error deleting document", e) }
            }
        }.addOnFailureListener { e -> Log.w("Firebase", "Error deleting documents", e)}
    }

    // Called before providing the '_markets' (to the MarketFragment)
    private fun updateMarketsTotals() {
        var itemQuantity = 0
        var itemUPC = ""
        var productPricesList = ProductPriceList(3)

        _markets.value!![0].cartPrice = 0f
        _markets.value!![1].cartPrice = 0f
        _markets.value!![2].cartPrice = 0f

        cart.itemQuantityMap.forEach {
            itemUPC = it.key
            itemQuantity = it.value.quantity

            productPricesList = productPricesByShopMap.get(itemUPC)!!
            _markets.value!![0].cartPrice += productPricesList.list[0].price * itemQuantity
            _markets.value!![1].cartPrice += productPricesList.list[1].price * itemQuantity
            _markets.value!![2].cartPrice += productPricesList.list[2].price * itemQuantity
        }
    }
}