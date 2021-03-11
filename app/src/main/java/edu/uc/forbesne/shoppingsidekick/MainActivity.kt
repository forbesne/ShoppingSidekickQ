package edu.uc.forbesne.shoppingsidekick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import edu.uc.forbesne.shoppingsidekick.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
                    supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

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

}