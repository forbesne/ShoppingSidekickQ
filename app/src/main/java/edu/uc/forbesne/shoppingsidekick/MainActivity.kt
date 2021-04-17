package edu.uc.forbesne.shoppingsidekick

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.uc.forbesne.shoppingsidekick.dto.Market
import edu.uc.forbesne.shoppingsidekick.ui.main.*
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment
    private lateinit var marketFragment: MarketFragment
    private lateinit var activeFragment: Fragment
    private lateinit var cartFragment: CartFragment
    private lateinit var storeFragment2: StoreFragment2
    private lateinit var mapsFragment: MapsFragment

    private lateinit var viewModel: MainViewModel
    var isUserSignedIn = false

    // For testing - only create firebaseAuth instances from inside a function
    var isFirstTimeUserClickedLogin = true

    private val AUTH_REQUEST_CODE = 1701

    internal var user : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //var storeName ="Shoprite"
        setContentView(R.layout.main_activity)
        mainFragment = MainFragment.newInstance()
        marketFragment = MarketFragment.newInstance()
        cartFragment = CartFragment.newInstance()
        //storeFragment = StoreFragment.newInstance()

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
                    //replaceFragment(storeFragment)
                    //replaceFragment(storeFragment2)
                }
                R.id.profile -> {
                    login()
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

    fun displayStoreFragment(store: Market) {
        storeFragment2 = StoreFragment2.newInstance(store)
        if (activeFragment != storeFragment2) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, storeFragment2)
                .commitNow()
            activeFragment = storeFragment2
        }
    }

    fun displayMapsFragment(store: Market) {
        mapsFragment = MapsFragment.newInstance(store)
        if (activeFragment != mapsFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mapsFragment)
                .commitNow()
            activeFragment = mapsFragment
        }
    }

    fun replaceFragment(fragment: Fragment){
        if (fragment != null) {

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
    }

    private fun login() {
        if(isFirstTimeUserClickedLogin == true){
            isFirstTimeUserClickedLogin = false
            checkIsUserSignedIn()
        }

        if(isUserSignedIn == false){
            var providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build()
            )
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTheme(R.style.Theme_ShoppingSidekick)
                            .build(), AUTH_REQUEST_CODE
            )
        } else{
            isUserSignedIn = false
            FirebaseAuth.getInstance().signOut()
            viewModel.getUserCartOnLogin()

            replaceFragment(mainFragment)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == AUTH_REQUEST_CODE) {
                user = FirebaseAuth.getInstance().currentUser
                isUserSignedIn = true
                viewModel.getUserCartOnLogin()

                replaceFragment(mainFragment)
            }
        }
    }

    private fun checkIsUserSignedIn(){
        var firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth != null && firebaseAuth.currentUser != null){
            isUserSignedIn = true
        }
    }

}