package edu.uc.forbesne.shoppingsidekick.ui.main
// based code on Top Ten project https://github.com/IsaiahDicristoforo/Top-Ten

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.Product
import android.transition.TransitionManager
import kotlinx.android.synthetic.main.main_activity.*

class ProductListAdapter( private val productList: ArrayList<Product>):RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
     lateinit var mContext: Context

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.product_list_item,viewGroup, false)
        mContext = viewGroup.context
        return  ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productCard = view
        val img: ImageView = view.findViewById(R.id.imgProduct)
        val upc: TextView = view.findViewById(R.id.txtUpc)
        val description: TextView = view.findViewById(R.id.txtDesc)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(productList[position].imageURL).into(holder.img);
        holder.upc.text = productList[position].UPC
        holder.description.text = productList[position].description

        holder.productCard.setOnClickListener(){
            holder.productCard.isClickable = false
            //open pop-up for product
            openProductPopUp(holder.upc.toString())
        }
    }

    //From https://android--code.blogspot.com/2018/02/android-kotlin-popup-window-example.html
    private fun openProductPopUp(upc:String) {
        // Initialize a new layout inflater instance
        val inflater:LayoutInflater = mContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.product_popup,null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
            LinearLayout.LayoutParams.MATCH_PARENT // Window height
        )

        // Create a new slide animation for popup window enter transition
        val slideIn = Slide()
        slideIn.slideEdge = Gravity.TOP
        popupWindow.enterTransition = slideIn

        // Slide animation for popup window exit transition
        val slideOut = Slide()
        slideOut.slideEdge = Gravity.RIGHT
        popupWindow.exitTransition = slideOut


        //TransitionManager.beginDelayedTransition(rltvProducts)
        popupWindow.showAtLocation(
            view, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
    }



    override fun getItemCount(): Int {
        return productList.size
    }
}