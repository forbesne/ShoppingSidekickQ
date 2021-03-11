package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.forbesne.shoppingsidekick.dto.*
import edu.uc.forbesne.shoppingsidekick.service.ProductService

//I used the professor's code from his github - https://github.com/discospiff/MyPlantDiaryQ
// as the base for this code

/**
 *  Gets data from server, makes adjustments, provides live data to activities
 *
 */
class MainViewModel : ViewModel() {
    var productService: ProductService = ProductService()
    var products: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productsFromShop1: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productsFromShop2: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productsFromShop3: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var cart: Cart = Cart(ArrayList<CartItem>())

    var searchItemList: ArrayList<SearchItem> = ArrayList<SearchItem>()

    // A list of object{shopName, shopsPrice}
    var shops1ProductPriceList :Shops1ProductPriceList = Shops1ProductPriceList(3)
    // A map/table containing all key value pairs where key = productUPC, value =object{shopName, shopPrice}
    var productPricesByShopMap: HashMap<String,Shops1ProductPriceList> = HashMap<String, Shops1ProductPriceList>()

    init {
        fetchSop1Products()
        fetchSop2Products()
        fetchSop3Products()
        assignProducts()
        populateShops1ProductPriceList()
        createObservableShopsPricesMap()
    }

    private fun populateShops1ProductPriceList() {
        shops1ProductPriceList.list[0].shopName = "Shop1"
        shops1ProductPriceList.list[1].shopName = "Shop2"
        shops1ProductPriceList.list[2].shopName = "Shop3"
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
                    arr0!!.list[0].price = product.price
                    productPricesByShopMap.put(product.UPC, arr0)
                }
            }
        }

        productsFromShop2.observeForever {
            it.forEach {
                product-> {
                    var arr1 = productPricesByShopMap.get(product.UPC)
                    arr1!!.list[1].price = product.price
                    productPricesByShopMap.put(product.UPC, arr1)
                }
            }
        }

        productsFromShop3.observeForever {
            it.forEach {
                product-> {
                    var arr2 = productPricesByShopMap.get(product.UPC)
                    arr2!!.list[1].price = product.price
                    productPricesByShopMap.put(product.UPC, arr2)
                }
            }
        }
    }

    fun fetchProductsByName(productName: String) {
        productsFromShop1 = productService.fetchProductsByName(productName)
    }

    fun findCheapestMarket() {
        var cart1Price = 0f
        var cart2Price = 0f
        var cart3Price = 0f
        var itemAmount = 0
        var itemUPC = ""


        cart.itemList.forEach{
            itemAmount = it.amount
            itemUPC = it.UPC

        }
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