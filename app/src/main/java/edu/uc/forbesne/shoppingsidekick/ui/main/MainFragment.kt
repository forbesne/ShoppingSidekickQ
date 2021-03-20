package edu.uc.forbesne.shoppingsidekick.ui.main
// based code on Top Ten project https://github.com/IsaiahDicristoforo/Top-Ten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.MainActivity
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.product_popup.*

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : ProductListAdapter
    private lateinit var cartItem : CartItem


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var btnFindCheapestMarket: Button = this.activity!!.findViewById(R.id.button5)
        var recyclerView = view!!.findViewById<RecyclerView>(R.id.recLstProducts)
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)

        // => Commented out Until btn is available
        /*btnAddProduct.setOnClickListener {
            addCartItem()
        }*/

        btnIncrease.setOnClickListener{
            val qty = cartItem.quantity + 1
            cartItem.add(qty)
            etnQuantity.text = qty.toString()
        }

        btnDecrease.setOnClickListener{
            val qty = cartItem.quantity - 1
            cartItem.reduce(qty)
            etnQuantity.text = qty.toString()
        }

        viewModel.fetchAllProducts()
        viewModel.products.observe(this, Observer { products ->
            actProductName.setAdapter(
                    ArrayAdapter(
                            context!!,
                            android.R.layout.simple_spinner_dropdown_item,
                            products
                    )
            )
            adapter = ProductListAdapter(
                    viewModel.products.value!!, viewModel
            )
            recyclerView.adapter = adapter
        })

        btnFindCheapestMarket.setOnClickListener{
            viewModel.findCheapestMarket()
        }
    }

    // This will get called when the user clicks on the pop-up window.
    private fun addCartItem() {
        // The cart item logic should mostly be in the view model and cart item is not created by the user.
        // Instead we need only the quantity and a way to identify a product - for all shops - done by UPC
        // => Commented out until view has these fields
       /* var quantity = etnQuantity.text.toString()
        var upc = upc.text.toString() //perhaps this should be stored in a hidden label
        viewModel.addCartItem(cartItem) */
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}