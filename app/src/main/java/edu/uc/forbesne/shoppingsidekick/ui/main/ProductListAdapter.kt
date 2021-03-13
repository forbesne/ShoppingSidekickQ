package edu.uc.forbesne.shoppingsidekick.ui.main
// based code on Top Ten project https://github.com/IsaiahDicristoforo/Top-Ten

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.Product

class ProductListAdapter( private val productList: ArrayList<Product>):RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.product_list_item,viewGroup, false)
        return  ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view
        val img: ImageView = view.findViewById(R.id.imgProduct)
        val upc: TextView = view.findViewById(R.id.txtUpc)
        val descreption: TextView = view.findViewById(R.id.txtDesc)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(productList[position].imageURL).into(holder.img);
        holder.upc.text = productList[position].UPC
        holder.descreption.text = productList[position].description

        holder.card.setOnClickListener(){
            holder.card.isClickable = false
            //open pop-up for product
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}