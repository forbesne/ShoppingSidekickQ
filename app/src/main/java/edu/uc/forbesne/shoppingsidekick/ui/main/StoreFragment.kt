package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.store_fragment.*

class StoreFragment : Fragment() {

    companion object {
        fun newInstance() = StoreFragment()
    }

//    private lateinit var viewModel: StoreViewModel
    private lateinit var viewModel: MainViewModel
    private var _cartItems = java.util.ArrayList<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.store_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.fetchCartItem()
        storeView.hasFixedSize()
        storeView.layoutManager = LinearLayoutManager(context)
        storeView.itemAnimator = DefaultItemAnimator()
        storeView.adapter = StoreAdapter(_cartItems, R.layout.store_fragment_row)

        viewModel.cartItem.observeForever{
            cartItem ->
            _cartItems.removeAll(_cartItems)
            _cartItems.addAll(cartItem)
            if(storeView!=null){
                storeView.adapter!!.notifyDataSetChanged()
            }
        }
    }

    inner class StoreAdapter(val cartItems: List<CartItem>, val itemLayout: Int) : RecyclerView.Adapter<StoreFragment.StoreViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreFragment.StoreViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
            return StoreViewHolder(view)
        }

        override fun getItemCount(): Int {
            return cartItems.size
        }

        override fun onBindViewHolder(holder: StoreFragment.StoreViewHolder, position: Int) {
            val cartItem = cartItems.get(position)
            holder.updateStore(cartItem)
        }

    }

    inner class StoreViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var storeImage : ImageView = itemView.findViewById(R.id.imageView)
        //        private var lblUPC :t TextView = itemView.findViewById(R.id.lblUPC)
        private var productName : TextView = itemView.findViewById(R.id.productname)
        private var brandName : TextView = itemView.findViewById(R.id.brandname)
        private var lblQuantity : TextView = itemView.findViewById(R.id.lblquantity)
        private var unitLbl : TextView = itemView.findViewById(R.id.unitlbl)


        fun updateStore (cartItem : CartItem) {
            if (cartItem.imageURL != null && cartItem.imageURL != "null" && cartItem.imageURL != "") {
                Picasso.get().load(cartItem.imageURL).into(storeImage);
                /*val source = ImageDecoder.createSource(activity!!.contentResolver, Uri.parse(cartItem.imageURL))
                val bitmap = ImageDecoder.decodeBitmap(source)
                cartProductImage.setImageBitmap(bitmap)*/
            }
//            lblUPC.text = cartItem.toString()
            productName.text = cartItem.description
            brandName.text = cartItem.productBrand
            lblQuantity.text = cartItem.quantity.toString()
            unitLbl.text = cartItem.measurementUnit
        }
    }
}