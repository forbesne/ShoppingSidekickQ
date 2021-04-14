package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.MainActivity
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.CartItem
import edu.uc.forbesne.shoppingsidekick.dto.Market
import kotlinx.android.synthetic.main.market_fragment_row.*

class StoreFragment2(store:Market) : Fragment() {

    var store = store

    companion object {
        fun newInstance(store: Market) = StoreFragment2(store)
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : StoreListAdapter

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

        var recyclerView = view!!.findViewById<RecyclerView>(R.id.storeView)
        recyclerView.layoutManager =  GridLayoutManager(this.context, 1)
        var storeCartItems: ArrayList<CartItem> = ArrayList<CartItem>()

        viewModel.markets?.observe(this, Observer {
            shops ->
            shops.forEach { shop ->
                if (shop.name == store.name) {

                    adapter = StoreListAdapter(
                            shop.cartItems, viewModel
                    )
                    recyclerView.adapter = adapter
                }
            }

        })


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

}