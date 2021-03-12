package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.CartItem

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // => Commented out Until btn is available
        /*btnAddProduct.setOnClickListener {
            addCartItem()
        }*/
    }

    // This will get called when the user clicks on the pop-up window.
    private fun addCartItem() {
        // The cart item logic should mostly be in the view model and cart item is not created by the user.
        // Instead we need only the quantity and a way to identify a product - for all shops - done by UPC
        // => Commented out until view has these fields
        /*var quantity = etnQuantity.text.toString()
        var upc = upc.text.toString() //perhaps this should be stored in a hidden label
        viewModel.addCartItem(cartItem)*/



        /*var cartItem = CartItem().apply{
            productName = lblProductName.text.toString()
            productBrand = lblProductBrand.text.toString()
            measurementUnit = lblUnitValue.text.toString()
            cartId = 0
            quantity = 1
        }*/
    }
}