package edu.uc.forbesne.shoppingsidekick.ui.main
// based code on Top Ten project https://github.com/IsaiahDicristoforo/Top-Ten

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.Product

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
        val upc: TextView = view.findViewById(R.id.txtUpc)
        val description: TextView = view.findViewById(R.id.txtDesc)
        val imgURL: TextView = view.findViewById(R.id.imgURL)
        val brand: TextView = view.findViewById(R.id.prodBrand)
        val unitValue: TextView = view.findViewById(R.id.prodUnitValue)
        val img: ImageView = view.findViewById(R.id.imgProduct)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(productList[position].imageURL).into(holder.img);
        holder.imgURL.text = productList[position].imageURL
        holder.upc.text = productList[position].UPC
        holder.description.text = productList[position].description
        holder.brand.text = productList[position].brand
        holder.unitValue.text = productList[position].price_type

        holder.productCard.setOnClickListener(){
            //holder.productCard.isClickable = false
            //open pop-up for product
            openProductPopUp(holder.upc.text as String, holder.description.text as String,
                    holder.imgURL.text as String,holder.brand.text as String,holder.unitValue.text as String)
        }
    }

    //From https://android--code.blogspot.com/2018/02/android-kotlin-popup-window-example.html
    private fun openProductPopUp(upc: String, description: String, imgURL: String, brand: String, unitValue: String) {
        // Initialize a new layout inflater instance
        val inflater:LayoutInflater = mContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view_popup = inflater.inflate(R.layout.product_popup,null)
        val view_backdrop = inflater.inflate(R.layout.backdrop,null)

        val prodName: TextView = view_popup.findViewById(R.id.lblProductName)
        val prodBrand: TextView = view_popup.findViewById(R.id.lblProductBrand)
        val prodUPC: TextView = view_popup.findViewById(R.id.lblProductUpc)
        val prodUnitValue: TextView = view_popup.findViewById(R.id.lblUnitValue)
        val prodImg: ImageView = view_popup.findViewById(R.id.productImage)
        val btnAddProductToCart: Button = view_popup.findViewById(R.id.btnAddProduct)

        Picasso.get().load(imgURL).into(prodImg);
        prodName.text = description
        prodUPC.text = upc
        prodBrand.text = brand
        prodUnitValue.text = unitValue
        //prodUPC.setGravity(Gravity.CENTER);

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view_popup, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )
        val backdropWindow = PopupWindow(
            view_backdrop, // Custom view to show in popup window
            LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
            LinearLayout.LayoutParams.MATCH_PARENT // Window height
        )

        // Create a new slide animation for popup window enter transition
        //val slideIn = Slide()
        //slideIn.slideEdge = Gravity.TOP
        //popupWindow.enterTransition = slideIn

        // Slide animation for popup window exit transition
        //val slideOut = Slide()
        //slideOut.slideEdge = Gravity.RIGHT
        //popupWindow.exitTransition = slideOut

        view_backdrop.setOnClickListener{
            // Dismiss the popup window
            popupWindow.dismiss()
            backdropWindow.dismiss()
        }

        //TransitionManager.beginDelayedTransition(rltvProducts)
        backdropWindow.showAtLocation(
            view_backdrop, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
        popupWindow.showAtLocation(
            view_popup, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )

        btnAddProductToCart.setOnClickListener{
            //call add to cart on viewModel
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}