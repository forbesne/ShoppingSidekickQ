package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.CartItem

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

//    Most things below will need to be changed in accordance with the layout for the cart

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        // TODO: Use the ViewModel

//        These have been commented out to allow the project to compile

       /* rcyCartItems.hasFixedSize()
        rcyCartItems.layoutManager = LinearLayoutManager(context)
        rcyCartItems.itemAnimator = DefaultItemAnimator()
        rcyCartItems.adapter = CartAdapter(viewModel.product.cartItems, R.layout.Cart_List_Item)*/
    }

//    saveCart might not be needed, adding it just in case as that's what the professor had in his video.

    private fun saveCart() {
        var cartItem = CartItem()
        with (cartItem) {
            /*productBrand = actproductBrand.text.toString()
            description = edtDescription.text.toString
            var quantityString = edtQuantity.text.toString();
            if (quantityString.length > 0) {
                quantity = quantityString.toDouble()
            }
            measurementUnit = actMeasurementUnit.text.toString()*/
        }
        /*viewModel.product.cartItems.add(cartItem)
        clearAll()
        rcyCartItems.adapter?.notifyDataSetChanged()*/
    }

    inner class CartAdapter(val cartItems: List<CartItem>, val itemLayout: Int) : RecyclerView.Adapter<CartFragment.CartViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
            return CartViewHolder(view)
        }

        override fun getItemCount(): Int {
            return cartItems.size
        }

        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            val cartItem = cartItems.get(position)
            holder.updateCart(cartItem)
        }

    }

    inner class CartViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var productImage : ImageView = itemView.findViewById(R.id.productImage)
        private var lblUPC : TextView = itemView.findViewById(R.id.lblUPC)



        fun updateCart (cartItem : CartItem) {
            lblUPC.text = cartItem.toString()

        }
    }

}