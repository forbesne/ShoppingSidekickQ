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
        // TODO: Use the ViewModel
        btnAddProduct.setOnClickListener {
            addCartItem()
        }
    }

    private fun addCartItem() {
        var cartItem = CartItem().apply{
            productName = lblProductName.text.toString()
            productBrand = lblProductBrand.text.toString()
            measurementUnit = lblUnitValue.text.toString()
            quantity = etnQuantity.text.toString()
            cartId = 0
            quantity = 1
        }
        viewModel.save(cartItem)
    }

}