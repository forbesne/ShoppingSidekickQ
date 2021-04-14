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
import kotlin.reflect.KFunction1


class MarketListAdapter(private val marketList: ArrayList<Market>, mainViewModel: MainViewModel,
                        callback: KFunction1<@ParameterName(name = "storeName") String, Unit>
)

    :RecyclerView.Adapter<MarketListAdapter.ViewHolder>(){

    var mvm = mainViewModel
    var callback = callback

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
        //val distance: TextView = view.findViewById(R.id.txtDistance)
        val latitude: TextView = view.findViewById(R.id.txtLatitude)
        val longitude: TextView = view.findViewById(R.id.txtLongitude)
        val mapIcon: ImageButton = view.findViewById(R.id.btnMap)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.name.text = marketList[position].name
        holder.cartPrice.text = "Price: $ ${"%.2f".format(marketList[position].cartPrice).toString()}"
        holder.latitude.text = " ${marketList[position].latitude.substring(0, 8)}"
        holder.longitude.text = "${marketList[position].longitude.substring(0, 8)}"

        //holder.distance.text = "Distance: ${"%.2f".format(marketList[position].distance).toString()} Miles"

        holder.name.setOnClickListener(){
            callback(holder.name.text.toString())
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
