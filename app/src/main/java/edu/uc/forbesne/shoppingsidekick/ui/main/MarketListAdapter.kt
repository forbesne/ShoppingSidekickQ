package edu.uc.forbesne.shoppingsidekick.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.Market


class MarketListAdapter (
    private val marketList: ArrayList<Market>, mainViewModel: MainViewModel) : RecyclerView.Adapter<MarketListAdapter.ViewHolder>(){

        var mvm = mainViewModel

        override fun onCreateViewHolder(
                viewGroup: ViewGroup,
                viewType: Int
        ): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.market,viewGroup, false)
            return  ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val marketCard = view
        val name: TextView = view.findViewById(R.id.txtName)
        val cartPrice: TextView = view.findViewById(R.id.txtCartPrice)
        val distance: TextView = view.findViewById(R.id.txtDistance)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.name.text = marketList[position].name

        holder.cartPrice.text = "Price: $ ${"%.2f".format(marketList[position].cartPrice).toString()}"
        holder.distance.text = "Distance: ${"%.2f".format(marketList[position].distance).toString()} Miles"

        // To be continued...
        /*holder.marketCard.setOnClickListener(){
        }

        btnName.setOnClickListener{
        }

        btnMap.setOnClickListener{
        }*/
    }

    override fun getItemCount(): Int {
        return marketList.size
    }
}
