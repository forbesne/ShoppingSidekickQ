package edu.uc.forbesne.shoppingsidekick
// based onp professor's code - https://github.com/discospiff/MyPlantDiaryQ

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.uc.forbesne.shoppingsidekick.dto.Cart
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import edu.uc.forbesne.shoppingsidekick.dto.Product
import edu.uc.forbesne.shoppingsidekick.service.FirebaseService
import edu.uc.forbesne.shoppingsidekick.ui.main.MainViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule
import java.lang.Thread.sleep

/**
 * Integration test, verifies market API call and some cart capabilities
 *
 */
class ProductDataIntegrationTest {

    @get:Rule
    var rule: TestRule =  InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel
    lateinit var cart: Cart
    var firebaseService = mockk<FirebaseService>()

    @Test
    fun confirmChips_outputsChips () {
        var product: Product = Product("100000001", 2.5f,"Item","Best Chips","Snacks",   "chipsImageURL","Chips",1)
        assertEquals("Description: Chips, Price: 2.5, Category: Snacks", product.toString());
    }

    @Test
    fun requestAllProductsFromShopOne_returnsAllProductsFromShopOne() {
        givenAViewModelIsAvailable()
        whenRequestAllProductsFromShopOne()
        thenResultIsNotEmpty()
    }

    private fun givenAViewModelIsAvailable() {
        mvm = MainViewModel()
    }

    private fun whenRequestAllProductsFromShopOne() {
        mvm.fetchAllProducts()
    }

    private fun thenResultIsNotEmpty() {
        // should makes sure thread is used only after enough time for response to be back
        sleep(4000)
        mvm.products.observeForever {
            assertNotNull(it)
            assertTrue(it.size > 0)
            //does work
            //assertTrue(it.size == 25)
        }
    }

    @Test
    fun requestCartFromFirebase_createsCartOfSize4() {
        givenAViewModelIsAvailable()
        whenRequestAllCartItemFromFirestore()
        thenCartSizeIs4()
    }

    private fun whenRequestAllCartItemFromFirestore(){
        createCartMuckData()
        cart = mvm.firebaseService.getCart()
    }

    private fun thenCartSizeIs4() {
        assertTrue(cart.size == 4)
    }

    fun createCartMuckData(){
        var cart = Cart()
        var cartItems = ArrayList<CartItem>()
        var cartItem1 = CartItem("111111111111",1,"item1Url",
            "First Product","The Best","1 fl oz",1f)
        var cartItem2 = CartItem("222222222222",2,"item1Url",
            "Second Product","The Best","2 fl oz",2f)
        var cartItem3 = CartItem("333333333333",3,"item1Url",
            "Third Product","The Best","3 fl oz",3f)
        var cartItem4 = CartItem("444444444444",4,"item1Url",
            "Fourth Product","The Best","4 pounds",4f)

        cartItems.add(cartItem1)
        cartItems.add(cartItem2)
        cartItems.add(cartItem3)
        cartItems.add(cartItem4)

        cart.addItems(cartItems)

        every {firebaseService.getCart() } returns cart
        mvm.firebaseService = firebaseService
    }
}