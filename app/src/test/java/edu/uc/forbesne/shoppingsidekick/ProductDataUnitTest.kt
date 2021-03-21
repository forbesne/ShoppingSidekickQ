package edu.uc.forbesne.shoppingsidekick

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import edu.uc.forbesne.shoppingsidekick.dto.Product
import edu.uc.forbesne.shoppingsidekick.service.ProductService
import edu.uc.forbesne.shoppingsidekick.ui.main.MainViewModel
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ProductDataUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel

    var productService = mockk<ProductService>()

    @Test
    fun confirmApple_outputsApple () {
        var product: Product = Product("100000001", 2.5f,"Item","Granny Smith","Fruit",   "appleImageURL","Apple", 1 )
        assertEquals("Description: Apple, Price: 2.5, Category: Fruit", product.toString());
    }

    @Test
    fun searchForApple_returnsApple() {
        givenAFeedOfMockedProductDataAreAvailable()
        whenSearchForApple()
        thenResultContainsApple()
        thenVerifyFunctionsInvoked()
    }

    private fun thenVerifyFunctionsInvoked() {
        verify {productService.fetchProductsByName("Apple")}
        verify(exactly = 0) {productService.fetchProductsByName("Orange")}
        confirmVerified(productService)
    }

    private fun givenAFeedOfMockedProductDataAreAvailable() {
        mvm = MainViewModel()
        createMockData()
    }

    private fun createMockData() {
        var allProductsLiveData = MutableLiveData<ArrayList<Product>>()
        var allProducts = ArrayList<Product>()
        var apple = Product("100000001", 2.5f,"Item",   "Granny Smith","Fruit", "appleImageURL","Apple",1)
        allProducts.add(apple)
        var banana = Product("100000002", 1.5f,"Item",  "Nuleaf","Fruit",  "bananaImageURL","Banana",1)
        allProducts.add(banana)

        allProductsLiveData.postValue(allProducts)
        every { productService.fetchProductsByName(or("Apple", "Banana")) } returns allProductsLiveData
        every { productService.fetchProductsByName(not(or("Apple", "Banana"))) } returns MutableLiveData<ArrayList<Product>>()
        mvm.productService = productService

    }

    private fun whenSearchForApple() {
        mvm.fetchProductsByName("Apple")
    }

    private fun thenResultContainsApple() {
        var appleFound = false;
        mvm.products.observeForever {
            assertNotNull(it)
            assertTrue(it.size > 0)
            it.forEach {
                if (it.brand == "Granny Smith" && it.description.contains("Apple")) {
                    appleFound = true;
                }
            }
            assertTrue(appleFound)
        }
    }

    @Test
    fun searchForGarbage_returnsNothing() {
        givenAFeedOfMockedProductDataAreAvailable()
        whenISearchForGarbage()
        thenIGetZeroResults()
    }

    private fun whenISearchForGarbage() {
        mvm.fetchProductsByName("alkdfjaioefnadfvka")
    }

    private fun thenIGetZeroResults() {
        mvm.products.observeForever {
            assertEquals(0, it.size)
        }
    }
}