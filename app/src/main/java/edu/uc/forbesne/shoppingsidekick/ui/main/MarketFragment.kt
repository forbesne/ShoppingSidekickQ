package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.MainActivity
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.Market

class MarketFragment : Fragment() {

    companion object {
        fun newInstance() = MarketFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : MarketListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.market_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }

        var recyclerView = view!!.findViewById<RecyclerView>(R.id.recMarketList)
        recyclerView.layoutManager =  GridLayoutManager(this.context, 1)
        var sortedByPriceMarketList: ArrayList<Market> = ArrayList<Market>()

        viewModel.markets?.observeForever{ markets ->

            sortedByPriceMarketList = markets

            // Cheapest market 1st
            sortedByPriceMarketList.sortBy { it.cartPrice }

            adapter = MarketListAdapter(
                    sortedByPriceMarketList, viewModel , (activity as MainActivity)::displayStoreFragment,
                (activity as MainActivity)::displayMapsFragment
            )
            recyclerView.adapter = adapter
        }
/*        txtName.setOnClickListener() {
            (activity as MainActivity).displayStoreFragment()
        }*/
    }
}