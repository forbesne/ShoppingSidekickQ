package edu.uc.forbesne.shoppingsidekick.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : ProductListAdapter

    //private var _events = ArrayList<UsageEvents.Event>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var recyclerView = view!!.findViewById<RecyclerView>(R.id.recLstProducts)

        viewModel.fetchAllProducts()
        viewModel.products.observe(this, Observer {
           products ->
            actProductName.setAdapter(
                ArrayAdapter(
                    context!!,
                    android.R.layout.simple_spinner_dropdown_item,
                    products
                )
            )
            adapter = ProductListAdapter(
                viewModel.products.value!!
            )
            recyclerView.adapter = adapter
        })
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}