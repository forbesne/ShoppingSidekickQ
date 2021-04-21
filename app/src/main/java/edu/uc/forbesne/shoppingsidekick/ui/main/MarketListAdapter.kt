package edu.uc.forbesne.shoppingsidekick.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.MapsActivity
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import edu.uc.forbesne.shoppingsidekick.dto.Market
import kotlin.reflect.KFunction1

class MarketListAdapter(
    private val marketList: ArrayList<Market>,
    mainViewModel: MainViewModel,
    callbackOpenStore: KFunction1<@ParameterName(name = "store") Market, Unit>,
    callbackOpenMaps: KFunction1<@ParameterName(name = "store") Market, Unit>
)

    :RecyclerView.Adapter<MarketListAdapter.ViewHolder>(){

    var mvm = mainViewModel
    var callbackOpenStore = callbackOpenStore
    var callbackOpenMaps = callbackOpenMaps

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
        val floatPrice: TextView = view.findViewById(R.id.floatCartPrice)
        val distance: TextView = view.findViewById(R.id.txtDistance)
        val latitude: TextView = view.findViewById(R.id.txtLatitude)
        val longitude: TextView = view.findViewById(R.id.txtLongitude)
        val mapIcon: ImageButton = view.findViewById(R.id.btnMap)
        val address: TextView = view.findViewById(R.id.txtAddress)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.name.text = marketList[position].name
        holder.floatPrice.text = "${"%.2f".format(marketList[position].cartPrice)}"
        holder.cartPrice.text = "Price: $ ${"%.2f".format(marketList[position].cartPrice).toString()}"
        holder.distance.text = " ${marketList[position].distance}"
        holder.latitude.text = " ${marketList[position].latitude}"
        holder.longitude.text = "${marketList[position].longitude}"
        holder.address.text = "${marketList[position].address}"

        holder.name.setOnClickListener(){
            var market: Market = Market(holder.name.text.toString(),holder.distance.text.toString().toFloat(),
                    holder.floatPrice.text.toString().toFloat(), holder.latitude.text.toString(),holder.longitude.text.toString(),
                    ArrayList<CartItem>(),holder.address.text.toString())
            callbackOpenStore(market)
        }

        holder.mapIcon.setOnClickListener{
            var market: Market = Market(holder.name.text.toString(),holder.distance.text.toString().toFloat(),
                holder.floatPrice.text.toString().toFloat(), holder.latitude.text.toString(),holder.longitude.text.toString(),
                ArrayList<CartItem>(),holder.address.text.toString())
            callbackOpenMaps(market)
        }
    }

    override fun getItemCount(): Int {
        return marketList.size
    }
}
