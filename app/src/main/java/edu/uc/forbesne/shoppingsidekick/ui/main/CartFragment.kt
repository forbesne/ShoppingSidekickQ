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
import kotlinx.android.synthetic.main.cart_fragment.*

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

//    Most things below will need to be changed in accordance with the layout for the cart

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

//        These have been commented out to allow the project to compile

        cartListView.hasFixedSize()
        cartListView.layoutManager = LinearLayoutManager(context)
        cartListView.itemAnimator = DefaultItemAnimator()
        cartListView.adapter = CartAdapter(viewModel.getCartItems(), R.layout.cart_fragment_row)
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
        private var cartProductImage : ImageView = itemView.findViewById(R.id.cartProductImage)
//        private var lblUPC :t TextView = itemView.findViewById(R.id.lblUPC)
        private var productName : TextView = itemView.findViewById(R.id.productName)
        private var productBrand : TextView = itemView.findViewById(R.id.productBrand)
        private var lblQuantity : TextView = itemView.findViewById(R.id.lblQuantity)
        private var unitLbl : TextView = itemView.findViewById(R.id.unitLbl)


        fun updateCart (cartItem : CartItem) {
//            if ()
//            lblUPC.text = cartItem.toString()
            productName.text = cartItem.toString()
            productBrand.text = cartItem.toString()
            lblQuantity.text = cartItem.toString()
            unitLbl.text = cartItem.toString()
        }
    }

}