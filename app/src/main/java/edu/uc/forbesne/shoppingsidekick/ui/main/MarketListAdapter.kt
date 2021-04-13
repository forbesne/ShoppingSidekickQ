package edu.uc.forbesne.shoppingsidekick.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.MapsActivity
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.Market


class MarketListAdapter (private val marketList: ArrayList<Market>, mainViewModel: MainViewModel)
    :RecyclerView.Adapter<MarketListAdapter.ViewHolder>(){

    var mvm = mainViewModel

    override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.market_fragment_row,viewGroup, false)
        return  ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val marketCard = view
        val name: TextView = view.findViewById(R.id.txtName)
        val cartPrice: TextView = view.findViewById(R.id.txtCartPrice)
        val distance: TextView = view.findViewById(R.id.txtDistance)
        val mapIcon: ImageButton = view.findViewById(R.id.btnMap)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.name.text = marketList[position].name

        holder.cartPrice.text = "Price: $ ${"%.2f".format(marketList[position].cartPrice).toString()}"
        holder.distance.text = "Distance: ${"%.2f".format(marketList[position].distance).toString()} Miles"

        //TODO get the latitude altitude
        // To be continued...
        holder.marketCard.setOnClickListener(){
            //TODO benjamin work on this!!
        }




/*        btnName.setOnClickListener{
        }*/

        holder.mapIcon.setOnClickListener{
            val intent = Intent(holder.marketCard.context, MapsActivity::class.java)
            holder.marketCard.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return marketList.size
    }
}
