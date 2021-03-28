package edu.uc.forbesne.shoppingsidekick.ui.main
// based code on Top Ten project https://github.com/IsaiahDicristoforo/Top-Ten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uc.forbesne.shoppingsidekick.MainActivity
import edu.uc.forbesne.shoppingsidekick.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : ProductListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }

        var btnFindCheapestMarket: Button = this.activity!!.findViewById(R.id.btnFindCheapestMarket)
        var recyclerView = view!!.findViewById<RecyclerView>(R.id.recLstProducts)
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)

        viewModel.fetchAllProducts()
        viewModel.products.observe(this, Observer { products ->
            actProductName.setAdapter(
                    ArrayAdapter(
                            context!!,
                            android.R.layout.simple_spinner_dropdown_item,
                            products
                    )
            )
            adapter = ProductListAdapter(
                    viewModel.products.value!!, viewModel
            )
            recyclerView.adapter = adapter
        })

        btnFindCheapestMarket.setOnClickListener{
            (activity as MainActivity).displayMarketFragment()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}