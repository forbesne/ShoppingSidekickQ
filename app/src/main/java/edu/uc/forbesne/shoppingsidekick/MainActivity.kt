package edu.uc.forbesne.shoppingsidekick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.uc.forbesne.shoppingsidekick.ui.main.*
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment
    private lateinit var marketFragment: MarketFragment
    private lateinit var activeFragment: Fragment
    private lateinit var cartFragment: CartFragment
    private lateinit var storeFragment: StoreFragment
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        mainFragment = MainFragment.newInstance()
        marketFragment = MarketFragment.newInstance()
        cartFragment = CartFragment.newInstance()
        storeFragment = StoreFragment.newInstance()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // This enable tests,
        // main activity triggers the methods that create the firebase instances
        viewModel.initialize()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()

            activeFragment = mainFragment

        }


        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(mainFragment)
                }
                R.id.history -> {
                    replaceFragment(marketFragment)
                }
                R.id.cart -> {
                    replaceFragment(cartFragment)
                }
                R.id.more -> {
                    replaceFragment(storeFragment)
                }
                R.id.profile -> {
                    replaceFragment(storeFragment)
                }
            }
            true
        }



    }


    fun displayMarketFragment(){
        if (activeFragment == mainFragment) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, marketFragment)
                .commitNow()
            activeFragment = marketFragment

        }
    }

    fun displayCartFragment() {
        if (activeFragment != cartFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, cartFragment)
                .commitNow()
            activeFragment = cartFragment
        }
    }

    fun displayStoreFragment() {
        if (activeFragment != storeFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, storeFragment)
                .commitNow()
            activeFragment = storeFragment
        }
    }


    fun replaceFragment(fragment: Fragment){
        if (fragment != null) {

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()

        }
    }



}