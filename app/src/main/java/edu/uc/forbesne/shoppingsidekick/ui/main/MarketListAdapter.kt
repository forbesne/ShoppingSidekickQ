package edu.uc.forbesne.shoppingsidekick.ui.main

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
import edu.uc.forbesne.shoppingsidekick.dto.Market

/*
class MarketListAdapter (private val marketList: ArrayList<Market>, mainViewModel: MainViewModel):RecyclerView.Adapter<MarketListAdapter.ViewHolder>(){
    lateinit var mContext: Context
    var mvm = mainViewModel

    override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.market_list_item,viewGroup, false)
        mContext = viewGroup.context
        return  ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val marketCard = view
        val name: TextView = view.findViewById(R.id.txtName)
        val cartPrice: TextView = view.findViewById(R.id.txtPrice)
        val distance: TextView = view.findViewById(R.id.txtDistance)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.name.text = marketList[position].name
        holder.cartPrice.text = marketList[position].cartPrice
        holder.distance.text = marketList[position].distance

        holder.marketCard.setOnClickListener(){

        }

        btnName.setOnClickListener{

        }

        btnMap.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return marketList.size
    }
}*/
