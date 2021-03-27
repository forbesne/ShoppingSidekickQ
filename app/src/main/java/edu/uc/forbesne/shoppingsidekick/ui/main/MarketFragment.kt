package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.MainActivity
import edu.uc.forbesne.shoppingsidekick.R
import kotlinx.android.synthetic.main.main_fragment.*

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
        return inflater.inflate(R.layout.result_display, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }

        var recyclerView = view!!.findViewById<RecyclerView>(R.id.recMarketList)
        //recyclerView.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
        recyclerView.layoutManager =  GridLayoutManager(this.context, 1)

        // For now this is needed to update the markets data in mvm.
        viewModel.findCheapestMarket()

        viewModel.markets.observe(this, Observer { market ->

            adapter = MarketListAdapter(
                    viewModel.markets.value!!, viewModel
            )
            recyclerView.adapter = adapter
        })

    }
}