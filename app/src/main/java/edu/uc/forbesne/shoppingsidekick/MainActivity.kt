package edu.uc.forbesne.shoppingsidekick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.uc.forbesne.shoppingsidekick.ui.main.MainFragment
import edu.uc.forbesne.shoppingsidekick.ui.main.MainViewModel
import edu.uc.forbesne.shoppingsidekick.ui.main.MarketFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment
    private lateinit var marketFragment: MarketFragment
    private lateinit var activeFragment: Fragment
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        mainFragment = MainFragment.newInstance()
        marketFragment = MarketFragment.newInstance()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()

            activeFragment = mainFragment

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater;
        inflater.inflate(R.menu.shopping_sidekick_menu,menu);

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itmCart -> Toast.makeText(this,"Cart Selected", Toast.LENGTH_SHORT).show()
            R.id.itmClearCart -> {
                viewModel.deleteCart();

            };
        }

        return super.onOptionsItemSelected(item)
    }

     fun displayMarketFragment(){
         if (activeFragment == mainFragment) {

             supportFragmentManager.beginTransaction()
                     .replace(R.id.container, marketFragment)
                     .commitNow()
             activeFragment = marketFragment

         }
    }
}