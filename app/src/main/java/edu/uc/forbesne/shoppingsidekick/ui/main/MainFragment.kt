package edu.uc.forbesne.shoppingsidekick.ui.main
// based code on Top Ten project https://github.com/IsaiahDicristoforo/Top-Ten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : ProductListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var recyclerView = view!!.findViewById<RecyclerView>(R.id.recLstProducts)
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)

        // => Commented out Until btn is available
        /*btnAddProduct.setOnClickListener {
            addCartItem()
        }*/

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
                    viewModel.products.value!!
            )
            recyclerView.adapter = adapter
        })
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

    companion object {
        fun newInstance() = MainFragment()
    }
}