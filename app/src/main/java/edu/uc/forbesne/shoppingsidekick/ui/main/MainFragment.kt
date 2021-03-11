package edu.uc.forbesne.shoppingsidekick.ui.main


import android.app.usage.UsageEvents
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.uc.forbesne.shoppingsidekick.R
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var _events = ArrayList<UsageEvents.Event>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel


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

            //lstProducts.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, products)

        })



    }

}