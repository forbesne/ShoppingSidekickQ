package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.uc.forbesne.shoppingsidekick.MainActivity
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import edu.uc.forbesne.shoppingsidekick.dto.Market
import kotlinx.android.synthetic.main.market_fragment_row.*
import kotlinx.android.synthetic.main.store_fragment.*

class StoreFragment2(store:Market) : Fragment(), OnMapReadyCallback {

    var store = store
    var latitude = store.latitude
    var longitude = store.longitude
    companion object {
        fun newInstance(store: Market) = StoreFragment2(store)
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : StoreListAdapter
    private lateinit var mapView: MapView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.store_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }

        var storeName = view!!.findViewById<TextView>(R.id.txtStore)
        storeName.text = store.name
        var storeAddress = view!!.findViewById<TextView>(R.id.txtAddress)
        storeAddress.text = store.address.replace(',', '\n')
        var storeTotal = view!!.findViewById<TextView>(R.id.txtTotal)
        storeTotal.text = "Total: $ ${"%.2f".format(store.cartPrice).toString()}"

        mapView = view!!.findViewById(R.id.mapView3)
        val mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)

        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        var recyclerView = view!!.findViewById<RecyclerView>(R.id.storeView)
        recyclerView.layoutManager =  GridLayoutManager(this.context, 1)
        var storeCartItems: ArrayList<CartItem> = ArrayList<CartItem>()

        viewModel.markets?.observeForever(){
            shops ->
            shops.forEach { shop ->
                if (shop.name == store.name) {

                    adapter = StoreListAdapter(
                            shop.cartItems, viewModel
                    )
                    recyclerView.adapter = adapter
                }
            }

        }


        /*viewModel.markets?.observe(this, Observer { markets ->

            sortedByPriceMarketList = markets

            // Cheapest market 1st
            sortedByPriceMarketList.sortBy { it.cartPrice }

            adapter = MarketListAdapter(
                    sortedByPriceMarketList, viewModel
            )
            recyclerView.adapter = adapter
        })
        txtName.setOnClickListener() {
            (activity as MainActivity).displayStoreFragment()
        }*/
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY) ?: Bundle().also {
            outState.putBundle(MAPVIEW_BUNDLE_KEY, it)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onMapReady(map: GoogleMap) {
        val marker = LatLng(latitude.toDouble(), longitude.toDouble())
        map.addMarker(MarkerOptions().position(marker).title(store.name))
        map.moveCamera(CameraUpdateFactory.newLatLng(marker))
        map.moveCamera(CameraUpdateFactory.zoomTo(10F))
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}