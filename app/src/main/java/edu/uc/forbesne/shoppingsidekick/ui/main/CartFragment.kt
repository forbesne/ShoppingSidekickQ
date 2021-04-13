package edu.uc.forbesne.shoppingsidekick.ui.main

import android.graphics.ImageDecoder
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import kotlinx.android.synthetic.main.cart_fragment.*

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _cartItems = java.util.ArrayList<CartItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

//    Most things below will need to be changed in accordance with the layout for the cart

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

//        These have been commented out to allow the project to compile
        viewModel.fetchCartItem()
        cartListView.hasFixedSize()
        cartListView.layoutManager = LinearLayoutManager(context)
        cartListView.itemAnimator = DefaultItemAnimator()
        cartListView.adapter = CartAdapter(_cartItems, R.layout.cart_fragment_row)

        viewModel.cartItem.observe(this, Observer{
            cartItem ->
            _cartItems.removeAll(_cartItems)
            _cartItems.addAll(cartItem)
            cartListView.adapter!!.notifyDataSetChanged()
        })

       btnClearCart.setOnClickListener() {
            viewModel.emptyCart()
        }
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
        private var unitLbl : TextView = itemView.findViewById(R.id.UnitValueLbl)
        private var btnIncrease : ImageButton = itemView.findViewById(R.id.increaseBtn)
        private var btnDecrease : ImageButton = itemView.findViewById(R.id.decreaseBtn)
        private var btnRemove : ImageButton = itemView.findViewById(R.id.btnRemove)


        fun updateCart (cartItem : CartItem) {
            if (cartItem.imageURL != null && cartItem.imageURL != "null" && cartItem.imageURL != "") {
                Picasso.get().load(cartItem.imageURL).into(cartProductImage);
                /*val source = ImageDecoder.createSource(activity!!.contentResolver, Uri.parse(cartItem.imageURL))
                val bitmap = ImageDecoder.decodeBitmap(source)
                cartProductImage.setImageBitmap(bitmap)*/
            }
//            lblUPC.text = cartItem.toString()
            productName.text = cartItem.description
            productBrand.text = cartItem.productBrand
            lblQuantity.text = cartItem.quantity.toString()
            unitLbl.text = cartItem.measurementUnit

            btnIncrease.setOnClickListener {
                var strQuantity = lblQuantity.text.toString()
                var quantity = strQuantity.toInt() + 1
                lblQuantity.text = quantity.toString()

                viewModel.firebaseService.adjustCartItemQuantityInFirebase(cartItem, 1)
            }

            btnDecrease.setOnClickListener {
                var strQuantity = lblQuantity.text.toString()
                var quantity = strQuantity.toInt()
                if (quantity > 1)  quantity -= 1
                else quantity = 1

                lblQuantity.text = quantity.toString()

                viewModel.firebaseService.adjustCartItemQuantityInFirebase(cartItem, -1)
            }

            btnRemove.setOnClickListener {
                viewModel.removeFromCart(cartItem)
            }
        }
    }

}