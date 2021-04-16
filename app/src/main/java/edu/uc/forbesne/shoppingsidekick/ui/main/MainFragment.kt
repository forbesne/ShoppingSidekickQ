package edu.uc.forbesne.shoppingsidekick.ui.main
// based code on Top Ten project https://github.com/IsaiahDicristoforo/Top-Ten

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.uc.forbesne.shoppingsidekick.MainActivity
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.Product
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.market_fragment_row.*


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : ProductListAdapter
    private val AUTH_REQUEST_CODE = 1701
    private var user : FirebaseUser? = null

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
            /*actProductName.setAdapter(
                    ArrayAdapter(
                            context!!,
                            android.R.layout.simple_spinner_dropdown_item,
                            products
                    )
            )*/
            adapter = ProductListAdapter(
                    viewModel.products.value!!, viewModel
            )
            recyclerView.adapter = adapter
        })

        btnFindCheapestMarket.setOnClickListener{
            (activity as MainActivity).displayMarketFragment()
        }

        searchProduct.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

                filter(s.toString())
            }
        })

        /*btnLogin.setOnClickListener {
            login()
        }

        btnSave.setOnClickListener{
            (activity as MainActivity).displayCartFragment()
        }

        btnTakePhoto.setOnClickListener{
            val intent = Intent(context, MapsActivity::class.java)
            startActivity(intent)
        }*/
    }
    fun filter(text: String) {
        val temp: ArrayList<Product> = ArrayList()
        for (d in viewModel.products.value!!) {
            if (d.description.toLowerCase().contains(text.toLowerCase())) {
                temp.add(d)
            }
        }
        adapter.updateProductList(temp)
    }
    private fun login() {
        var providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), AUTH_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == AUTH_REQUEST_CODE) {
                user = FirebaseAuth.getInstance().currentUser
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}