package com.example.materialdesigndemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var adapter: FruitAdapter? = null

    val fruit = mutableListOf(Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango))

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        navView.setCheckedItem(R.id.navCall)
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            true
        }
        fab.setOnClickListener {view ->
            Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    Toast.makeText(this, "FAB Click", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        initRefreshLayout()
        initFruits()
        initRv()
    }

    private fun initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruit.size).random()
            fruitList.add(fruit[index])
        }
    }

    private fun initRefreshLayout() {
        swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary)
        swipeRefresh.setOnRefreshListener {
            refreshFruits()
        }
    }

    private fun refreshFruits() {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter?.notifyDataSetChanged()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun initRv() {
        val layoutManager = GridLayoutManager(this, 2)
        mRv.layoutManager = layoutManager
        adapter = FruitAdapter(this, fruitList)
        mRv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.backup -> {
                Toast.makeText(this, "You click backup", Toast.LENGTH_SHORT).show()
            }
            R.id.delete -> {
                Toast.makeText(this, "You click delete", Toast.LENGTH_SHORT).show()
            }
            R.id.setting -> {
                Toast.makeText(this, "You click settings", Toast.LENGTH_SHORT).show()
            }
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }
}