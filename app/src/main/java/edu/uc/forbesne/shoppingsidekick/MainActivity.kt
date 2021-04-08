package edu.uc.forbesne.shoppingsidekick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()

            activeFragment = mainFragment

        }


        bottom_nav.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(mainFragment)
                R.id.history -> replaceFragment(storeFragment)
                R.id.cart -> replaceFragment(cartFragment)
                R.id.more -> replaceFragment(storeFragment)


            }
        }



    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater;
        inflater.inflate(R.menu.shopping_sidekick_menu,menu);

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item2 -> Toast.makeText(this,"cart Selected", Toast.LENGTH_SHORT).show()
            R.id.item3 -> Toast.makeText(this,"item3 Selected", Toast.LENGTH_SHORT).show()
            R.id.item4 -> Toast.makeText(this,"item4 Selected", Toast.LENGTH_SHORT).show()
            R.id.item5 -> Toast.makeText(this,"item5 Selected", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
    */

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