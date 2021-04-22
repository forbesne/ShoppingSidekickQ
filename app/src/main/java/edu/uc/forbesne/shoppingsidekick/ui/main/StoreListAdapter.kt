package edu.uc.forbesne.shoppingsidekick.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.CartItem

class StoreListAdapter (private val cartItems: ArrayList<CartItem>, mainViewModel: MainViewModel)
        : RecyclerView.Adapter<StoreListAdapter.ViewHolder>(){

    var mvm = mainViewModel

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.store_fragment_row,viewGroup, false)
        return  ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.productname)
        val brandName: TextView = view.findViewById(R.id.brandname)
        val lblQuantity: TextView = view.findViewById(R.id.lblquantity)
        val txtPrice: TextView = view.findViewById(R.id.txtprice)
        val lblUnitValue: TextView = view.findViewById(R.id.unitvaluelbl)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.productName.text = cartItems[position].description
        holder.brandName.text = cartItems[position].productBrand
        holder.lblQuantity.text = cartItems[position].quantity.toString()
        holder.txtPrice.text = cartItems[position].price.toString()
        holder.lblUnitValue.text = cartItems[position].measurementUnit
        Picasso.get().load(cartItems[position].imageURL).into(holder.imageView);
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}